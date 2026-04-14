package com.petshop.dto;

import jakarta.validation.constraints.*;

public class PetRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Breed is required")
    private String breed;

    @Min(value = 0, message = "Age must be 0 or greater")
    private int age;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be 0 or greater")
    private Double price;

    private String description;
    private String imageUrl;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Integer categoryId;

    // no-arg constructor — required for @RequestBody deserialization
    public PetRequestDTO() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}
