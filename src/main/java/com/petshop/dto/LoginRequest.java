package com.petshop.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}