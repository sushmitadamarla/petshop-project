package com.petshop.controller;

import com.petshop.dto.CustomerDTO;
import com.petshop.entity.Customer;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    // REGISTER
    @PostMapping
    public Customer register(@RequestBody Customer customer) {
        return service.registerCustomer(customer);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Customer update(@PathVariable int id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    // GET ONE
    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable int id) {
        return service.getCustomer(id);
    }

    // GET ALL
    @GetMapping
    public List<CustomerDTO> getAll() {
        return service.getAllCustomers();
    }
}
