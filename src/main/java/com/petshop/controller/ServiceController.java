package com.petshop.controller;

import com.petshop.dto.ServiceDTO;
import com.petshop.entity.GroomingService;
import com.petshop.entity.Vaccination;
import com.petshop.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceManager serviceManager;

    // ADD
    @PostMapping("/grooming")
    public GroomingService addGrooming(@RequestBody GroomingService g) {
        return serviceManager.addGrooming(g);
    }

    @PostMapping("/vaccination")
    public Vaccination addVaccination(@RequestBody Vaccination v) {
        return serviceManager.addVaccination(v);
    }

    // ASSIGN
    @PostMapping("/assign/grooming")
    public String assignGrooming(@RequestParam int petId, @RequestParam int serviceId) {
        return serviceManager.assignGrooming(petId, serviceId);
    }

    // minor update
    @PostMapping("/assign/vaccination")
    public String assignVaccination(@RequestParam int petId, @RequestParam int vaccinationId) {
        return serviceManager.assignVaccination(petId, vaccinationId);
    }

    @GetMapping("/grooming")
    public List<ServiceDTO> getAllGrooming() {
        return serviceManager.getAllGrooming();
    }

    @GetMapping("/vaccination")
    public List<ServiceDTO> getAllVaccination() {
        return serviceManager.getAllVaccination();
    }
}