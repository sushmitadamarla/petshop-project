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

    @Mock private GroomingServiceRepository groomingRepo;
    @Mock private VaccinationRepository vaccinationRepo;
    @Mock private PetRepository petRepo;

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

    // ================= ADD GROOMING =================

    @Test
    void addGrooming_success() {
        when(groomingRepo.save(any(GroomingService.class))).thenReturn(grooming);

        GroomingServiceDTO dto = new GroomingServiceDTO(0, "Bath", "Full cleaning", 500, true);
        GroomingServiceDTO result = serviceManager.addGrooming(dto);

        assertNotNull(result);
        assertEquals("Bath", result.getName());
        assertEquals("Full cleaning", result.getDescription());
        assertEquals(500, result.getPrice());
        assertTrue(result.isAvailable());
        verify(groomingRepo, times(1)).save(any(GroomingService.class));
    }

    // ================= ADD VACCINATION =================

    @Test
    void addVaccination_success() {
        when(vaccinationRepo.save(any(Vaccination.class))).thenReturn(vaccination);

        VaccinationDTO dto = new VaccinationDTO(0, "Rabies", "Rabies vaccine", 300, true);
        VaccinationDTO result = serviceManager.addVaccination(dto);

        assertNotNull(result);
        assertEquals("Rabies", result.getName());
        assertEquals("Rabies vaccine", result.getDescription());
        assertEquals(300, result.getPrice());
        verify(vaccinationRepo, times(1)).save(any(Vaccination.class));
    }

    // ================= ASSIGN GROOMING =================

    @Test
    void assignGrooming_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(groomingRepo.findById(10)).thenReturn(Optional.of(grooming));

        String result = serviceManager.assignGrooming(1, 10);

        assertEquals("Grooming assigned", result);
        verify(petRepo).save(pet);
    }

    @Test
    void assignGrooming_duplicate_throwsIllegalStateException() {
        pet.getGroomingServices().add(grooming);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(groomingRepo.findById(10)).thenReturn(Optional.of(grooming));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                serviceManager.assignGrooming(1, 10));

        assertEquals("Grooming service already assigned to this pet", ex.getMessage());
        verify(petRepo, never()).save(any()); // save should NOT be called
    }

    @Test
    void assignGrooming_petNotFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.assignGrooming(99, 10));

        verify(groomingRepo, never()).findById(any());
        verify(petRepo, never()).save(any());
    }

    @Test
    void assignGrooming_serviceNotFound_throwsException() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(groomingRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.assignGrooming(1, 99));

        verify(petRepo, never()).save(any());
    }

    // ================= ASSIGN VACCINATION =================

    @Test
    void assignVaccination_success() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(vaccinationRepo.findById(5)).thenReturn(Optional.of(vaccination));

        String result = serviceManager.assignVaccination(1, 5);

        assertEquals("Vaccination assigned successfully", result);
        verify(petRepo).save(pet);
    }

    @Test
    void assignVaccination_alreadyAssigned_returnsMessage() {
        pet.getVaccinations().add(vaccination);

        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(vaccinationRepo.findById(5)).thenReturn(Optional.of(vaccination));

        String result = serviceManager.assignVaccination(1, 5);

        assertEquals("Vaccination already assigned to this pet", result);
        verify(petRepo, never()).save(any()); // save should NOT be called
    }

    @Test
    void assignVaccination_petNotFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.assignVaccination(99, 5));

        verify(vaccinationRepo, never()).findById(any());
        verify(petRepo, never()).save(any());
    }

    @Test
    void assignVaccination_vaccinationNotFound_throwsException() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));
        when(vaccinationRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.assignVaccination(1, 99));

        verify(petRepo, never()).save(any());
    }

    // ================= GET ALL GROOMING =================

    @Test
    void getAllGrooming_returnsList() {
        when(groomingRepo.findAll()).thenReturn(List.of(grooming));

        List<GroomingServiceDTO> result = serviceManager.getAllGrooming();

        assertEquals(1, result.size());
        assertEquals("Bath", result.get(0).getName());
    }

    @Test
    void getAllGrooming_emptyList() {
        when(groomingRepo.findAll()).thenReturn(List.of());

        List<GroomingServiceDTO> result = serviceManager.getAllGrooming();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ================= GET ALL VACCINATION =================

    @Test
    void getAllVaccination_returnsList() {
        when(vaccinationRepo.findAll()).thenReturn(List.of(vaccination));

        List<VaccinationDTO> result = serviceManager.getAllVaccination();

        assertEquals(1, result.size());
        assertEquals("Rabies", result.get(0).getName());
    }

    @Test
    void getAllVaccination_emptyList() {
        when(vaccinationRepo.findAll()).thenReturn(List.of());

        List<VaccinationDTO> result = serviceManager.getAllVaccination();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ================= PET GROOMING HISTORY =================

    @Test
    void getPetGroomingHistory_success() {
        pet.getGroomingServices().add(grooming);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<GroomingServiceDTO> result = serviceManager.getPetGroomingHistory(1);

        assertEquals(1, result.size());
        assertEquals("Bath", result.get(0).getName());
    }

    @Test
    void getPetGroomingHistory_emptyHistory() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet)); // pet has no grooming

        List<GroomingServiceDTO> result = serviceManager.getPetGroomingHistory(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getPetGroomingHistory_petNotFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.getPetGroomingHistory(99));
    }

    // ================= PET VACCINATION HISTORY =================

    @Test
    void getPetVaccinationHistory_success() {
        pet.getVaccinations().add(vaccination);
        when(petRepo.findById(1)).thenReturn(Optional.of(pet));

        List<VaccinationDTO> result = serviceManager.getPetVaccinationHistory(1);

        assertEquals(1, result.size());
        assertEquals("Rabies", result.get(0).getName());
    }

    @Test
    void getPetVaccinationHistory_emptyHistory() {
        when(petRepo.findById(1)).thenReturn(Optional.of(pet)); // pet has no vaccinations

        List<VaccinationDTO> result = serviceManager.getPetVaccinationHistory(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getPetVaccinationHistory_petNotFound_throwsException() {
        when(petRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                serviceManager.getPetVaccinationHistory(99));
    }
}