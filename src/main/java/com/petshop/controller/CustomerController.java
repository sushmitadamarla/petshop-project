package com.petshop.controller;

import com.petshop.dto.CustomerDTO;
import com.petshop.dto.LoginRequest;
import com.petshop.entity.Address;
import com.petshop.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> register(@Valid @RequestBody CustomerDTO dto) {
        return new ResponseEntity<>(service.registerCustomer(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable int id,
                                              @Valid @RequestBody CustomerDTO dto) {
        return ResponseEntity.ok(service.updateCustomer(id, dto));
    }

    // ================= GET CUSTOMER =================
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable int id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<Address> getAddress(@PathVariable int id) {
        return ResponseEntity.ok(service.getAddress(id));
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<Address> addAddress(@PathVariable int id,
                                              @RequestBody Address address) {
        return new ResponseEntity<>(service.addAddress(id, address), HttpStatus.CREATED);
    }
}