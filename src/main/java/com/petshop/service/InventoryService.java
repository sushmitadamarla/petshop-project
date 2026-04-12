package com.petshop.service;

import com.petshop.dto.EmployeeDTO;
import com.petshop.entity.Employee;
import com.petshop.entity.Pet;
import com.petshop.repository.EmployeeRepository;
import com.petshop.repository.PetFoodRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired private PetRepository petRepo;
    @Autowired private EmployeeRepository employeeRepo;
    @Autowired private PetFoodRepository foodRepo;
    @Autowired private SupplierRepository supplierRepo;

    // ===== ASSIGN METHODS =====

    public String assignEmployee(int petId, int employeeId) {
        Pet pet = petRepo.findById(petId).orElse(null);
        Employee emp = employeeRepo.findById(employeeId).orElse(null);

        if (pet == null || emp == null) return "Not found";

        pet.getEmployees().add(emp);
        petRepo.save(pet);

        return "Employee assigned";
    }

    public String assignFood(int petId, int foodId) {
        Pet pet = petRepo.findById(petId).orElse(null);
        PetFood food = foodRepo.findById(foodId).orElse(null);

        if (pet == null || food == null) return "Not found";

        pet.getFoods().add(food);
        petRepo.save(pet);

        return "Food assigned";
    }

    public String assignSupplier(int petId, int supplierId) {
        Pet pet = petRepo.findById(petId).orElse(null);
        Supplier sup = supplierRepo.findById(supplierId).orElse(null);

        if (pet == null || sup == null) return "Data Not found";

        pet.getSuppliers().add(sup);
        petRepo.save(pet);

        return "Supplier assigned";
    }

    public EmployeeDTO convert(Employee e) {
        return new EmployeeDTO(
                e.getEmployeeId(),
                e.getFirstName(),
                e.getLastName(),
                e.getPosition()
        );
    }
}