package com.petshop.controller;

import com.petshop.dto.GroomingServiceDTO;
import com.petshop.dto.VaccinationDTO;
import com.petshop.entity.GroomingService;
import com.petshop.entity.Vaccination;
import com.petshop.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceManager serviceManager;

    // ================= ADD SERVICES =================

    @PostMapping("/grooming")
    public ResponseEntity<GroomingServiceDTO> addGrooming(@RequestBody GroomingServiceDTO dto) {
        return new ResponseEntity<>(
                serviceManager.addGrooming(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/vaccinations")
    public ResponseEntity<VaccinationDTO> addVaccination(@RequestBody VaccinationDTO dto) {
        return new ResponseEntity<>(
                serviceManager.addVaccination(dto),
                HttpStatus.CREATED
        );
    }

    // ================= ASSIGN SERVICES =================

    @PostMapping("/pets/{petId}/grooming/{serviceId}")
    public String assignGrooming(@PathVariable int petId,
                                 @PathVariable int serviceId) {
        return serviceManager.assignGrooming(petId, serviceId);
    }

    @PostMapping("/pets/{petId}/vaccinations/{vaccinationId}")
    public String assignVaccination(@PathVariable int petId,
                                    @PathVariable int vaccinationId) {
        return serviceManager.assignVaccination(petId, vaccinationId);
    }

    // ================= GET ALL SERVICES =================

    @GetMapping("/grooming")
    public List<GroomingServiceDTO> getAllGrooming() {
        return serviceManager.getAllGrooming();
    }

    @GetMapping("/vaccinations")
    public List<VaccinationDTO> getAllVaccination() {
        return serviceManager.getAllVaccination();
    }

    // ================= PET HISTORY =================

    @GetMapping("/pets/{petId}/grooming")
    public List<GroomingServiceDTO> getPetGroomingHistory(@PathVariable int petId) {
        return serviceManager.getPetGroomingHistory(petId);
    }

    @GetMapping("/pets/{petId}/vaccinations")
    public List<VaccinationDTO> getPetVaccinationHistory(@PathVariable int petId) {
        return serviceManager.getPetVaccinationHistory(petId);
    }
}