package com.example.rms.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) { this.courseRepository = courseRepository;}

    public List<Course> getCourses() {return courseRepository.findAll();}

    public Optional<Course> getCourse(Long courseId) {
        boolean studentExists = courseRepository.existsById(courseId);
        if(!studentExists) {
            throw new IllegalStateException("course does not exist");
        }

        return courseRepository.findById(courseId);
    }

    public void addNewCourse(Course course) {
        Optional<Course> courseByTitle = courseRepository.findCourseByTitle(course.getTitle());
        if(courseByTitle.isPresent()){
            throw new IllegalStateException("Course already exists");
        }
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        boolean courseExists = courseRepository.existsById(courseId);
        if(!courseExists) {
            throw new IllegalStateException("course does not exist");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String title, Integer duration) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalStateException("course does not exist"));

        if(title != null && title.length() > 0 && !Objects.equals(course.getTitle(), title)){
            course.setTitle(title);
        }

        if(duration != null && duration > 0 && course.getDuration() != duration ){
            course.setDuration(duration);
        }
    }
}
