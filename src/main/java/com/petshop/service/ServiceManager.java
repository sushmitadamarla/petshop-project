package com.petshop.service;

import com.petshop.dto.GroomingServiceDTO;
import com.petshop.dto.VaccinationDTO;
import com.petshop.entity.GroomingService;
import com.petshop.entity.Pet;
import com.petshop.entity.Vaccination;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.GroomingServiceRepository;
import com.petshop.repository.PetRepository;
import com.petshop.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceManager {

    @Autowired
    private GroomingServiceRepository groomingRepo;

    @Autowired
    private VaccinationRepository vaccinationRepo;

    @Autowired
    private PetRepository petRepo;

    // ================= ADD SERVICES =================

    public GroomingServiceDTO addGrooming(GroomingServiceDTO dto) {
        GroomingService g = new GroomingService();
        g.setName(dto.getName());
        g.setDescription(dto.getDescription());
        g.setPrice(dto.getPrice());
        g.setAvailable(dto.isAvailable());

        return mapGrooming(groomingRepo.save(g));
    }

    public VaccinationDTO addVaccination(VaccinationDTO dto) {
        Vaccination v = new Vaccination();
        v.setName(dto.getName());
        v.setDescription(dto.getDescription());
        v.setPrice(dto.getPrice());
        v.setAvailable(dto.isAvailable());

        return mapVaccination(vaccinationRepo.save(v));
    }

    // ================= ASSIGN SERVICES =================

    public String assignGrooming(int petId, int serviceId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        GroomingService g = groomingRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Grooming service not found"));

        pet.getGroomingServices().add(g);
        petRepo.save(pet);
        return "Grooming assigned";
    }

    public String assignVaccination(int petId, int vaccinationId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Vaccination v = vaccinationRepo.findById(vaccinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination not found"));

        pet.getVaccinations().add(v);
        petRepo.save(pet);
        return "Vaccination assigned";
    }

    // ================= DTO MAPPERS =================

    // BUG FIX: was calling g.getServiceName() → correct is g.getName()
    // BUG FIX: was passing only 3 args → now passing all 5
    private GroomingServiceDTO mapGrooming(GroomingService g) {
        return new GroomingServiceDTO(
                g.getServiceId(),      // FIX: was g.getId()
                g.getName(),           // FIX: was g.getServiceName()
                g.getDescription(),
                g.getPrice(),
                g.isAvailable()
        );
    }

    // BUG FIX: was calling v.getId() → correct is v.getVaccinationId()
    private VaccinationDTO mapVaccination(Vaccination v) {
        return new VaccinationDTO(
                v.getVaccinationId(),  // FIX: was v.getId()
                v.getName(),
                v.getDescription(),
                v.getPrice(),
                v.isAvailable()
        );
    }

    // ================= GET ALL SERVICES =================

    public List<GroomingServiceDTO> getAllGrooming() {
        return groomingRepo.findAll().stream().map(this::mapGrooming).toList();
    }

    public List<VaccinationDTO> getAllVaccination() {
        return vaccinationRepo.findAll().stream().map(this::mapVaccination).toList();
    }

    // ================= PET HISTORY =================

    public List<GroomingServiceDTO> getPetGroomingHistory(int petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return pet.getGroomingServices().stream().map(this::mapGrooming).toList();
    }

    public List<VaccinationDTO> getPetVaccinationHistory(int petId) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return pet.getVaccinations().stream().map(this::mapVaccination).toList();
    }
}