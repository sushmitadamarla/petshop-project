package com.petshop.controller;

import com.petshop.dto.PetDTO;
import com.petshop.dto.PetDetailsDTO;
import com.petshop.entity.Pet;
import com.petshop.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
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
    public Pet updatePet(@PathVariable int id, @RequestBody Pet pet) {
        return petService.updatePet(id, pet);
    }

    @GetMapping("/{id}/details")
    public PetDetailsDTO getPetDetails(@PathVariable int id) {
        return petService.getPetDetails(id);
    }
}