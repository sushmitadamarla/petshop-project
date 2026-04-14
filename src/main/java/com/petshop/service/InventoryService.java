package com.petshop.service;

import com.petshop.dto.EmployeeDTO;
import com.petshop.dto.PetFoodDTO;
import com.petshop.dto.PetFoodRequestDTO;
import com.petshop.dto.SupplierDTO;
import com.petshop.dto.SupplierRequestDTO;
import com.petshop.entity.Employee;
import com.petshop.entity.Pet;
import com.petshop.entity.PetFood;
import com.petshop.entity.Supplier;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.EmployeeRepository;
import com.petshop.repository.PetFoodRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired private PetRepository petRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private PetFoodRepository foodRepo;
    @Autowired private SupplierRepository supplierRepo;

    // ================= ASSIGN METHODS =================

    public String assignEmployee(int petId, int employeeId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        pet.getEmployees().add(emp);
        petRepo.save(pet);
        return "Employee assigned";
    }

    public String assignFood(int petId, int foodId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        PetFood food = foodRepo.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        pet.getFoods().add(food);
        petRepo.save(pet);
        return "Food assigned";
    }

    public String assignSupplier(int petId, int supplierId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        Supplier sup = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        pet.getSuppliers().add(sup);
        petRepo.save(pet);
        return "Supplier assigned";
    }

    // ================= FOOD APIs =================

    public List<PetFoodDTO> getAllFood() {
        return foodRepo.findAll().stream().map(this::mapFood).toList();
    }

    // GET single food by id
    public PetFoodDTO getFoodById(int id) {
        PetFood food = foodRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        return mapFood(food);
    }

    public PetFoodDTO addFood(PetFoodRequestDTO dto) {
        PetFood food = new PetFood();
        food.setName(dto.getName());
        food.setBrand(dto.getBrand());
        food.setType(dto.getType());
        food.setQuantity(dto.getQuantity());
        food.setPrice(dto.getPrice());
        return mapFood(foodRepo.save(food));
    }

    public PetFoodDTO updateFood(int id, PetFoodRequestDTO dto) {
        PetFood food = foodRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        food.setName(dto.getName());
        food.setBrand(dto.getBrand());
        food.setType(dto.getType());
        food.setQuantity(dto.getQuantity());
        food.setPrice(dto.getPrice());
        return mapFood(foodRepo.save(food));
    }

    // ================= SUPPLIER APIs =================

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepo.findAll().stream().map(this::mapSupplier).toList();
    }

    // GET single supplier by id
    public SupplierDTO getSupplierById(int id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        return mapSupplier(supplier);
    }

    public SupplierDTO addSupplier(SupplierRequestDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setEmail(dto.getEmail());
        return mapSupplier(supplierRepo.save(supplier));
    }

    // UPDATE supplier
    public SupplierDTO updateSupplier(int id, SupplierRequestDTO dto) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setEmail(dto.getEmail());
        return mapSupplier(supplierRepo.save(supplier));
    }

    public List<SupplierDTO> getSuppliersByPet(int petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return pet.getSuppliers().stream().map(this::mapSupplier).toList();
    }

    // ================= PET FOOD BY PET =================

    public List<PetFoodDTO> getFoodByPet(int petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return pet.getFoods().stream().map(this::mapFood).toList();
    }

    // ================= EMPLOYEE BY PET =================

    public List<EmployeeDTO> getEmployeesByPet(int petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return pet.getEmployees().stream().map(this::mapEmployee).toList();
    }

    // ================= MAPPERS =================

    private PetFoodDTO mapFood(PetFood f) {
        return new PetFoodDTO(f.getFoodId(), f.getName(), f.getBrand(), f.getPrice());
    }

    private SupplierDTO mapSupplier(Supplier s) {
        return new SupplierDTO(s.getSupplierId(), s.getName(), s.getContactPerson());
    }

    private EmployeeDTO mapEmployee(Employee e) {
        return new EmployeeDTO(e.getEmployeeId(), e.getFirstName(), e.getLastName(), e.getPosition());
    }
}