package com.petshop.controller;

import com.petshop.dto.PetFoodDTO;
import com.petshop.dto.SupplierDTO;
import com.petshop.entity.PetFood;
import com.petshop.entity.Supplier;
import com.petshop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;


    @PostMapping("/pets/{petId}/employees/{employeeId}")
    public String assignEmployee(@PathVariable int petId, @PathVariable int employeeId) {
        return service.assignEmployee(petId, employeeId);
    }

    @PostMapping("/pets/{petId}/food/{foodId}")
    public String assignFoodToPet(@PathVariable int petId, @PathVariable int foodId) {
        return service.assignFood(petId, foodId);
    }

    @PostMapping("/pets/{petId}/suppliers/{supplierId}")
    public String assignSupplier(@PathVariable int petId, @PathVariable int supplierId) {
        return service.assignSupplier(petId, supplierId);
    }

    @GetMapping("/food")
    public List<PetFoodDTO> getAllFood() {
        return service.getAllFood();
    }

    @PostMapping("/food")
    public ResponseEntity<PetFoodDTO> addFood(@RequestBody PetFood food) {
        return new ResponseEntity<>(service.addFood(food), HttpStatus.CREATED);
    }

    @PutMapping("/food/{id}")
    public ResponseEntity<PetFoodDTO> updateFood(@PathVariable int id, @RequestBody PetFood food) {
        return ResponseEntity.ok(service.updateFood(id, food));
    }


    @GetMapping("/suppliers")
    public List<SupplierDTO> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @PostMapping("/suppliers")
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody Supplier supplier) {
        return new ResponseEntity<>(service.addSupplier(supplier), HttpStatus.CREATED);
    }

    // ================= PET RELATIONSHIP QUERIES =================

    @GetMapping("/pets/{petId}/suppliers")
    public List<SupplierDTO> getSuppliersByPet(@PathVariable int petId) {
        return service.getSuppliersByPet(petId);
    }

    @GetMapping("/pets/{petId}/food")
    public List<PetFoodDTO> getFoodByPet(@PathVariable int petId) {
        return service.getFoodByPet(petId);
    }
}