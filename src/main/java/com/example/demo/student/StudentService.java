package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Email already taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists) {
            throw new IllegalStateException("Student does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    public Optional<Student> getStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists) {
            throw new IllegalStateException("Student does not exist");
        }

        return studentRepository.findById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String firstname, String lastname, String middlename, String email, String telephone, List<String> courses, String dob) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student does not exist"));

        if(firstname != null && firstname.length() > 0 && !Objects.equals(student.getFirstname(), firstname)){
            student.setFirstname(firstname);
            System.out.println("firstname check **************");
        }

        if(lastname != null && lastname.length() > 0 && !Objects.equals(student.getLastname(), lastname)){
            student.setLastname(lastname);
            System.out.println("lastname check **************");
        }

        if(middlename != null && middlename.length() > 0 && !Objects.equals(student.getMiddlename(), middlename)){
            student.setMiddlename(middlename);
            System.out.println("middlename check **************");
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            System.out.println("Email check **************");
            Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);


            if (studentEmail.isPresent()) {
                throw new IllegalStateException("Email already taken");
            } else {
                System.out.println("Email not taken");
                student.setEmail(email);
            }
        }

        if(telephone != null && telephone.length() > 0 && !Objects.equals(student.getTelephone(), telephone)){
            student.setTelephone(telephone);
            System.out.println("telephone check **************");
        }

        if(courses.isEmpty() != true ) {
//            && !courses.equals(student.getCourses())
            student.setCourses(courses);
        }

        if(dob != null && !Objects.equals(student.getDob(), dob)){
            student.setDob(LocalDate.parse(dob));
            System.out.println("dob check **************");
        }

    }
}
