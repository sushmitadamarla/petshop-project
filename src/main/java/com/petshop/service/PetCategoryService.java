package com.petshop.service;

import com.petshop.entity.PetCategory;
import com.petshop.repository.PetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetCategoryService {

    @Autowired
    private PetCategoryRepository categoryRepo;

    public PetCategory addCategory(PetCategory category) {
        return categoryRepo.save(category);
    }

    public List<PetCategory> getAllCategories() {
        return categoryRepo.findAll();
    }

    public PetCategory getCategoryById(int id) {
        return categoryRepo.findById(id).orElse(null);
    }

    public void deleteCategory(int id) {
        categoryRepo.deleteById(id);
    }
}