package com.example.student.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "student")

public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; // A unique identifier for each student (auto-generated).

    @Column(name = "name")
    private String name; // The student's full name.

    @Column
    @Email
    private String email; // The student's email address.

    @Column(name = "department")
    private String department;// The department to which the student belongs.

    public Student() {
        // no args
    }

    public Student(String name, @Email String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        
               
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
