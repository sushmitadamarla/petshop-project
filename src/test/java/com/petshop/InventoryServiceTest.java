package com.petshop;

import com.petshop.dto.*;
import com.petshop.entity.*;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.*;
import com.petshop.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock private PetRepository petRepo;
    @Mock private EmployeeRepository employeeRepo;
    @Mock private PetFoodRepository foodRepo;
    @Mock private SupplierRepository supplierRepo;

    @InjectMocks
    private InventoryService service;

    private Pet pet;
    private Employee emp;
    private PetFood food;
    private Supplier supplier;

    @BeforeEach
    void setup() {
        pet = new Pet();
        pet.setPetId(1);
        pet.setEmployees(new HashSet<>());
        pet.setFoods(new HashSet<>());
        pet.setSuppliers(new HashSet<>());

        emp = new Employee();
        emp.setEmployeeId(1);
        emp.setFirstName("John");
        emp.setLastName("Doe");
        emp.setPosition("Trainer");

        food = new PetFood();
        food.setFoodId(1);
        food.setName("Dog Food");
        food.setBrand("Pedigree");
        food.setType("Dry");
        food.setQuantity(10);
        food.setPrice(500);

        supplier = new Supplier();
        supplier.setSupplierId(1);
        supplier.setName("ABC Supplier");
        supplier.setContactPerson("Raj");
    }

    // ================= ASSIGN =================

    @Test
    void assignEmployee_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));

        String result = service.assignEmployee(1, 1);

        assertEquals("Employee assigned", result);
        assertTrue(pet.getEmployees().contains(emp));
        verify(petRepo).save(pet);
    }

    @Test
    void assignEmployee_petNotFound() {
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.assignEmployee(1, 1));
    }

    @Test
    void assignFood_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(foodRepo.findById(1)).thenReturn(Optional.of(food));

        String result = service.assignFood(1, 1);

        assertEquals("Food assigned", result);
        assertTrue(pet.getFoods().contains(food));
        verify(petRepo).save(pet);
    }

    @Test
    void assignFood_notFound() {
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.assignFood(1, 1));
    }

    @Test
    void assignSupplier_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(supplierRepo.findById(1)).thenReturn(Optional.of(supplier));

        String result = service.assignSupplier(1, 1);

        assertEquals("Supplier assigned", result);
        assertTrue(pet.getSuppliers().contains(supplier));
        verify(petRepo).save(pet);
    }

    @Test
    void assignSupplier_notFound() {
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.assignSupplier(1, 1));
    }

    // ================= FOOD =================

    @Test
    void getAllFood_success() {
        when(foodRepo.findAll()).thenReturn(List.of(food));

        List<PetFoodDTO> result = service.getAllFood();

        assertEquals(1, result.size());
        assertEquals("Dog Food", result.get(0).getName());
    }

    @Test
    void getFoodById_success() {
        when(foodRepo.findById(1)).thenReturn(Optional.of(food));

        PetFoodDTO result = service.getFoodById(1);

        assertEquals("Dog Food", result.getName());
    }

    @Test
    void getFoodById_notFound() {
        when(foodRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getFoodById(1));
    }

    @Test
    void addFood_success() {
        PetFoodRequestDTO dto = new PetFoodRequestDTO();
        dto.setName("Dog Food");
        dto.setBrand("Pedigree");
        dto.setType("Dry");
        dto.setQuantity(10);
        dto.setPrice(500.0);

        when(foodRepo.save(any(PetFood.class))).thenReturn(food);

        PetFoodDTO result = service.addFood(dto);

        assertEquals("Dog Food", result.getName());
    }

    @Test
    void updateFood_success() {
        PetFoodRequestDTO dto = new PetFoodRequestDTO();
        dto.setName("Updated Food");
        dto.setBrand("Brand");
        dto.setType("Wet");
        dto.setQuantity(5);
        dto.setPrice(600.0);

        when(foodRepo.findById(1)).thenReturn(Optional.of(food));
        when(foodRepo.save(any())).thenReturn(food);

        PetFoodDTO result = service.updateFood(1, dto);

        assertEquals("Updated Food", result.getName());
    }

    @Test
    void updateFood_notFound() {
        when(foodRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateFood(1, new PetFoodRequestDTO()));
    }

    // ================= SUPPLIER =================

    @Test
    void getAllSuppliers_success() {
        when(supplierRepo.findAll()).thenReturn(List.of(supplier));

        List<SupplierDTO> result = service.getAllSuppliers();

        assertEquals(1, result.size());
    }

    @Test
    void getSupplierById_success() {
        when(supplierRepo.findById(1)).thenReturn(Optional.of(supplier));

        SupplierDTO result = service.getSupplierById(1);

        assertEquals("ABC Supplier", result.getName());
    }

    @Test
    void getSupplierById_notFound() {
        when(supplierRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getSupplierById(1));
    }

    // ================= EMPLOYEE =================

    @Test
    void getAllEmployees_success() {
        when(employeeRepo.findAll()).thenReturn(List.of(emp));

        List<EmployeeDTO> result = service.getAllEmployees();

        assertEquals(1, result.size());
    }

    @Test
    void getEmployeeById_success() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));

        EmployeeDTO result = service.getEmployeeById(1);

        assertEquals("John", result.getFirstName());
    }

    @Test
    void getEmployeeById_notFound() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getEmployeeById(1));
    }

    // ================= RELATIONSHIP =================

    @Test
    void getFoodByPet_success() {
        pet.getFoods().add(food);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<PetFoodDTO> result = service.getFoodByPet(1);

        assertEquals(1, result.size());
    }

    @Test
    void getFoodByPet_notFound() {
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getFoodByPet(1));
    }

    @Test
    void getSuppliersByPet_success() {
        pet.getSuppliers().add(supplier);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<SupplierDTO> result = service.getSuppliersByPet(1);

        assertEquals(1, result.size());
    }

    @Test
    void getEmployeesByPet_success() {
        pet.getEmployees().add(emp);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<EmployeeDTO> result = service.getEmployeesByPet(1);

        assertEquals(1, result.size());
    }
}