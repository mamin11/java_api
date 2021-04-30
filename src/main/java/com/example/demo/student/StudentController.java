package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @CrossOrigin
    public List<Student> getStudents()
    {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    @CrossOrigin
    public Optional<Student> getStudent(@PathVariable("studentId") Long studentId){ return studentService.getStudent(studentId);}

    @PostMapping
    @CrossOrigin
    public void addNewStudent(@RequestBody Student student)
    {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    @CrossOrigin
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @CrossOrigin
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String middlename,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) List<String> courses,
            @RequestParam(required = false) String dob)
    {
        studentService.updateStudent(studentId, firstname, lastname, middlename, email, telephone, courses, dob);
    }
}
