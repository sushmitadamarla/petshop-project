package com.petshop.dto;

import com.petshop.entity.Address;

public class CustomerDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;

    public CustomerDTO(int id, String firstName, String lastName,
                       String email, String phone, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }
}