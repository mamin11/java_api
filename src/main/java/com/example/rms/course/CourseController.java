package com.example.rms.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {this.courseService = courseService;}


    @GetMapping
    @CrossOrigin
    public List<Course> getCourses() {return courseService.getCourses();}

    @GetMapping(path = "{courseId}")
    @CrossOrigin
    public Optional<Course> getCourse(@PathVariable("courseId") Long courseId) {return courseService.getCourse(courseId);}

    @PostMapping
    @CrossOrigin
    public void addNewCourse(@RequestBody Course course) {courseService.addNewCourse(course);}

    @DeleteMapping(path = "{courseId}")
    @CrossOrigin
    public void deleteCourse(@PathVariable("courseId") Long courseId) {courseService.deleteCourse(courseId);}

    @PutMapping(path = "{courseId}")
    @CrossOrigin
    public void updateCourse(@PathVariable("courseId") Long courseId,@RequestParam(required = false) String title, @RequestParam(required = false) Integer duration)
    {courseService.updateCourse(courseId,title,duration);}
}
