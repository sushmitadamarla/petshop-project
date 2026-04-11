package com.petshop.dto;

public class EmployeeDTO {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String position;

    public EmployeeDTO(int id, String fn, String ln, String pos) {
        this.employeeId = id;
        this.firstName = fn;
        this.lastName = ln;
        this.position = pos;
    }

    // getters

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPosition() {
        return position;
    }

    public String getLastName() {
        return lastName;
    }
}