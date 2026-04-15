package com.petshop;

import com.petshop.dto.GroomingServiceDTO;
import com.petshop.dto.VaccinationDTO;
import com.petshop.entity.GroomingService;
import com.petshop.entity.Pet;
import com.petshop.entity.Vaccination;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.GroomingServiceRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.VaccinationRepository;
import com.petshop.service.ServiceManager;

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
class ServiceManagerTest {

    @Mock
    private GroomingServiceRepository groomingRepo;

    @Mock
    private VaccinationRepository vaccinationRepo;

    @Mock
    private PetRepository petRepo;

    @InjectMocks
    private ServiceManager serviceManager;

    private Pet pet;
    private GroomingService grooming;
    private Vaccination vaccination;

    @BeforeEach
    void setup() {
        pet = new Pet();
        pet.setPetId(1);
        pet.setGroomingServices(new HashSet<>());
        pet.setVaccinations(new HashSet<>());
        
        grooming = new GroomingService();
        grooming.setServiceId(10);
        grooming.setName("Bath");
        grooming.setDescription("Full cleaning");
        grooming.setPrice(500);
        grooming.setAvailable(true);

        vaccination = new Vaccination();
        vaccination.setVaccinationId(5);
        vaccination.setName("Rabies");
        vaccination.setDescription("Rabies vaccine");
        vaccination.setPrice(300);
        vaccination.setAvailable(true);
    }

    // ================= ADD =================

    @Test
    void testAddGrooming() {
        when(groomingRepo.save(any(GroomingService.class))).thenReturn(grooming);

        GroomingServiceDTO dto = new GroomingServiceDTO(0, "Bath", "Full cleaning", 500, true);

        GroomingServiceDTO result = serviceManager.addGrooming(dto);

        assertNotNull(result);
        assertEquals("Bath", result.getName());
        verify(groomingRepo, times(1)).save(any(GroomingService.class));
    }

    @Test
    void testAddVaccination() {
        when(vaccinationRepo.save(any(Vaccination.class))).thenReturn(vaccination);

        VaccinationDTO dto = new VaccinationDTO(0, "Rabies", "Rabies vaccine", 300, true);
        
        VaccinationDTO result = serviceManager.addVaccination(dto);

        assertNotNull(result);
        assertEquals("Rabies", result.getName());
        verify(vaccinationRepo).save(any(Vaccination.class));
    }

    // ================= ASSIGN =================

    @Test
    void testAssignGrooming() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(groomingRepo.findById(10)).thenReturn(Optional.of(grooming));

        String result = serviceManager.assignGrooming(1, 10);

        assertEquals("Grooming assigned", result);
        verify(petRepo).save(pet);
    }

    @Test
    void testAssignGrooming_Duplicate() {
        pet.getGroomingServices().add(grooming);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(groomingRepo.findById(10)).thenReturn(Optional.of(grooming));

        assertThrows(IllegalStateException.class, () ->
                serviceManager.assignGrooming(1, 10)
        );
    }

    @Test
    void testAssignVaccination() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(vaccinationRepo.findById(5)).thenReturn(Optional.of(vaccination));

        String result = serviceManager.assignVaccination(1, 5);

        assertEquals("Vaccination assigned successfully", result);
        verify(petRepo).save(pet);
    }

    @Test
    void testAssignVaccination_AlreadyAssigned() {
        pet.getVaccinations().add(vaccination);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(vaccinationRepo.findById(5)).thenReturn(Optional.of(vaccination));

        String result = serviceManager.assignVaccination(1, 5);

        assertEquals("Vaccination already assigned to this pet", result);
    }

    // ================= GET ALL =================

    @Test
    void testGetAllGrooming() {
        when(groomingRepo.findAll()).thenReturn(List.of(grooming));

        List<GroomingServiceDTO> result = serviceManager.getAllGrooming();

        assertEquals(1, result.size());
    }

    @Test
    void testGetAllVaccination() {
        when(vaccinationRepo.findAll()).thenReturn(List.of(vaccination));

        List<VaccinationDTO> result = serviceManager.getAllVaccination();

        assertEquals(1, result.size());
    }

    // ================= HISTORY =================

    @Test
    void testGetPetGroomingHistory() {
        pet.getGroomingServices().add(grooming);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<GroomingServiceDTO> result = serviceManager.getPetGroomingHistory(1);

        assertEquals(1, result.size());
    }

    @Test
    void testGetPetVaccinationHistory() {
        pet.getVaccinations().add(vaccination);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<VaccinationDTO> result = serviceManager.getPetVaccinationHistory(1);

        assertEquals(1, result.size());
    }

    // ================= EXCEPTION =================

    @Test
    void testPetNotFound() {
        when(petRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.getPetGroomingHistory(1)
        );
    }
}
