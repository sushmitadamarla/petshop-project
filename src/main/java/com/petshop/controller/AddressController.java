package com.petshop.controller;

import com.petshop.entity.Address;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private CustomerService service;

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable int id, @RequestBody Address address) {
        return service.updateAddress(id, address);
    }
}