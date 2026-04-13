package com.petshop.service;

import com.petshop.dto.EmployeeDTO;
import com.petshop.dto.PetFoodDTO;
import com.petshop.dto.SupplierDTO;
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

        if (pet.getEmployees() == null) {
            pet.setEmployees(new ArrayList<>());
        }

        if (pet.getEmployees().contains(emp)) {
            return "Employee already assigned";
        }

        pet.getEmployees().add(emp);
        petRepo.save(pet);

        return "Employee assigned";
    }

    public String assignFood(int petId, int foodId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        PetFood food = foodRepo.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        if (pet.getFoods() == null) {
            pet.setFoods(new ArrayList<>());
        }

        pet.getFoods().add(food);
        petRepo.save(pet);

        return "Food assigned";
    }

    public String assignSupplier(int petId, int supplierId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Supplier sup = supplierRepo.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        if (pet.getSuppliers() == null) {
            pet.setSuppliers(new ArrayList<>());
        }

        pet.getSuppliers().add(sup);
        petRepo.save(pet);

        return "Supplier assigned";
    }

    // ================= MAPPERS =================

    private PetFoodDTO mapFood(PetFood f) {
        return new PetFoodDTO(
                f.getFoodId(),
                f.getName(),
                f.getBrand(),
                f.getPrice()
        );
    }

    private SupplierDTO mapSupplier(Supplier s) {
        return new SupplierDTO(
                s.getSupplierId(),
                s.getName(),
                s.getContactPerson()
        );
    }

    private EmployeeDTO mapEmployee(Employee e) {
        return new EmployeeDTO(
                e.getEmployeeId(),
                e.getFirstName(),
                e.getLastName(),
                e.getPosition()
        );
    }

    // ================= FOOD APIs =================

    public List<PetFoodDTO> getAllFood() {
        return foodRepo.findAll()
                .stream()
                .map(this::mapFood)
                .toList();
    }

    public PetFoodDTO addFood(PetFood food) {
        PetFood saved = foodRepo.save(food);
        return mapFood(saved);
    }

    public PetFoodDTO updateFood(int id, PetFood updated) {

        PetFood food = foodRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        food.setName(updated.getName());
        food.setBrand(updated.getBrand());
        food.setType(updated.getType());
        food.setQuantity(updated.getQuantity());
        food.setPrice(updated.getPrice());

        PetFood saved = foodRepo.save(food);
        return mapFood(saved);
    }

    // ================= SUPPLIER APIs =================

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepo.findAll()
                .stream()
                .map(this::mapSupplier)
                .toList();
    }

    public SupplierDTO addSupplier(Supplier supplier) {
        Supplier saved = supplierRepo.save(supplier);
        return mapSupplier(saved);
    }

    public List<SupplierDTO> getSuppliersByPet(int petId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        return pet.getSuppliers()
                .stream()
                .map(this::mapSupplier)
                .toList();
    }

    // ================= PET FOOD BY PET =================

    public List<PetFoodDTO> getFoodByPet(int petId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        return pet.getFoods()
                .stream()
                .map(this::mapFood)
                .toList();
    }
}