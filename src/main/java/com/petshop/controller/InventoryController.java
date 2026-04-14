package com.petshop.controller;

import com.petshop.dto.*;
import com.petshop.service.InventoryService;
import jakarta.validation.Valid;
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

    // ================= ASSIGN =================

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

    // ================= FOOD =================

    @GetMapping("/food")
    public List<PetFoodDTO> getAllFood() {
        return service.getAllFood();
    }

    @GetMapping("/food/{id}")
    public ResponseEntity<PetFoodDTO> getFoodById(@PathVariable int id) {
        return ResponseEntity.ok(service.getFoodById(id));
    }

    @PostMapping("/food")
    public ResponseEntity<PetFoodDTO> addFood(@Valid @RequestBody PetFoodRequestDTO dto) {
        return new ResponseEntity<>(service.addFood(dto), HttpStatus.CREATED);
    }

    @PutMapping("/food/{id}")
    public ResponseEntity<PetFoodDTO> updateFood(@PathVariable int id,
                                                 @Valid @RequestBody PetFoodRequestDTO dto) {
        return ResponseEntity.ok(service.updateFood(id, dto));
    }

    // ================= SUPPLIERS =================

    @GetMapping("/suppliers")
    public List<SupplierDTO> getAllSuppliers() {
        return service.getAllSuppliers();
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable int id) {
        return ResponseEntity.ok(service.getSupplierById(id));
    }

    @PostMapping("/suppliers")
    public ResponseEntity<SupplierDTO> addSupplier(@Valid @RequestBody SupplierRequestDTO dto) {
        return new ResponseEntity<>(service.addSupplier(dto), HttpStatus.CREATED);
    }

    @PutMapping("/suppliers/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable int id,
                                                      @Valid @RequestBody SupplierRequestDTO dto) {
        return ResponseEntity.ok(service.updateSupplier(id, dto));
    }

    // ================= EMPLOYEES =================

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return new ResponseEntity<>(service.addEmployee(dto), HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable int id,
                                                      @Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(service.updateEmployee(id, dto));
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

    @GetMapping("/pets/{petId}/employees")
    public List<EmployeeDTO> getEmployeesByPet(@PathVariable int petId) {
        return service.getEmployeesByPet(petId);
    }
}