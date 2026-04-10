package com.petshop.service;

import com.petshop.entity.Pet;
import com.petshop.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepo;

    public Pet addPet(Pet pet) {
        return petRepo.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public Pet getPetById(int id) {
        return petRepo.findById(id).orElse(null);
    }

    public List<Pet> getPetsByCategory(int categoryId) {
        return petRepo.findByCategory_CategoryId(categoryId);
    }

    public void deletePet(int id) {
        petRepo.deleteById(id);
    }
}