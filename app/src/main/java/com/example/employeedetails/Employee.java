package com.example.employeedetails;

public class Employee {
    int empId;
    String firstName, lastName, email, imgURL;
    public Employee(int empId, String firstName, String lastName, String email, String imgURL) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imgURL = imgURL;
    }

    public String getFirstName() {
        return firstName;
    }
}

