package com.petshop.dto;

public class PetDetailsDTO {

    private int petId;
    private String name;
    private String breed;
    private int age;
    private double price;
    private String description;
    private String imageUrl;

    private String categoryName;

    public PetDetailsDTO(int petId, String name, String breed, int age,
                         double price, String description, String imageUrl,
                         String categoryName) {
        this.petId = petId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    // Getters only (DTO should be immutable ideally)

    public int getPetId() { return petId; }
    public String getName() { return name; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getCategoryName() { return categoryName; }
}