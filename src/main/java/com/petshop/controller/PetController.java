package com.petshop.controller;

import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.dto.PetRequestDTO;
import com.petshop.entity.Pet;
import com.petshop.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet addPet(@Valid @RequestBody PetRequestDTO dto) {
        return petService.addPet(dto);
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable int id) {
        return petService.getPetById(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<Pet> getPetsByCategory(@PathVariable int categoryId) {
        return petService.getPetsByCategory(categoryId);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable int id, @Valid @RequestBody PetRequestDTO dto) {
        return petService.updatePet(id, dto);
    }

    @GetMapping("/{id}/details")
    public PetDetailsDTO getPetDetails(@PathVariable int id) {
        return petService.getPetDetails(id);
    }
}