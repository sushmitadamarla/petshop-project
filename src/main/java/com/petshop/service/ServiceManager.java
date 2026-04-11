package com.petshop.service;

import com.petshop.dto.ServiceDTO;
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

    // ADD SERVICES
    public GroomingService addGrooming(GroomingService g) {
        return groomingRepo.save(g);
    }

    public Vaccination addVaccination(Vaccination v) {
        return vaccinationRepo.save(v);
    }

    // ASSIGN SERVICES TO PET

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

    public ServiceDTO convertGroomingToDTO(GroomingService g) {
        return new ServiceDTO(g.getId(), g.getServiceName());
    }

    public ServiceDTO convertVaccinationToDTO(Vaccination v) {
        return new ServiceDTO(v.getId(), v.getVaccineName());
    }

    public List<ServiceDTO> getAllGrooming() {
        return groomingRepo.findAll()
                .stream()
                .map(this::convertGroomingToDTO)
                .toList();
    }

    public List<ServiceDTO> getAllVaccination() {
        return vaccinationRepo.findAll()
                .stream()
                .map(this::convertVaccinationToDTO)
                .toList();
    }
}