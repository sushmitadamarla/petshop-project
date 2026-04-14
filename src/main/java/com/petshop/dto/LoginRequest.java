package com.petshop.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;


    public LoginRequest() {}


    public LoginRequest(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}