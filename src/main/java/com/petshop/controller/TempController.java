package com.petshop.controller;

import com.petshop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class TempController {

    @Autowired
    private InventoryService service;

    @PostMapping("/assign/employee")
    public String assignEmployee(@RequestParam int petId, @RequestParam int employeeId) {
        return service.assignEmployee(petId, employeeId);
    }

    @PostMapping("/assign/food")
    public String assignFood(@RequestParam int petId, @RequestParam int foodId) {
        return service.assignFood(petId, foodId);
    }

    @PostMapping("/assign/supplier")
    public String assignSupplier(@RequestParam int petId, @RequestParam int supplierId) {
        return service.assignSupplier(petId, supplierId);
    }
}