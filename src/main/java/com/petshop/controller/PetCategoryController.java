package com.petshop.controller;

import com.petshop.entity.PetCategory;
import com.petshop.service.PetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class PetCategoryController {

    @Autowired
    private PetCategoryService categoryService;

    @PostMapping
    public PetCategory addCategory(@RequestBody PetCategory category) {
        return categoryService.addCategory(category);
    }

    @GetMapping
    public List<PetCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public PetCategory getCategory(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public PetCategory updateCategory(@PathVariable int id, @RequestBody PetCategory category) {
        return categoryService.updateCategory(id, category);
    }
}