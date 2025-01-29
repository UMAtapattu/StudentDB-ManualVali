package com.example.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.model.*;

import java.util.regex.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")

public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    private boolean isValidEmail(String email) {
        String emailFormatRegx = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern pattern = Pattern.compile(emailFormatRegx);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean isValidName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidDepartment(String department) {
        if (department != null && !department.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        try {

            List<Student> students = new ArrayList<Student>();
            studentRepository.findAll().forEach(students::add);
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/students")
    public ResponseEntity<Object> addCourse(@RequestBody Student studentToAdd) {
        if (!isValidEmail(studentToAdd.getEmail())) {
            return new ResponseEntity<>("Invalid email format. Please provide a valid email address.", HttpStatus.BAD_REQUEST);
        }
        if (!isValidName(studentToAdd.getName())) {
            return new ResponseEntity<>("Invalid name. Please provide a valid name.", HttpStatus.BAD_REQUEST);
        }
        if (!isValidDepartment(studentToAdd.getDepartment())) {
            return new ResponseEntity<>("Invalid department. Please provide a valid department.", HttpStatus.BAD_REQUEST);
        }
              
        
        try {

            var savedStudent = studentRepository.save(studentToAdd);

            return new ResponseEntity<>(new Object(){
                public final Student student = savedStudent;
                public final String message = "Student added successfully";}, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
