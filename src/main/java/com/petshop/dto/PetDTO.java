package com.petshop.dto;

public class PetDTO {

    private int petId;
    private String name;
    private String breed;
    private int age;
    private double price;

    // constructor
    public PetDTO(int petId, String name, String breed, int age, double price) {
        this.petId = petId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.price = price;
    }

    // getters
    public int getPetId() { return petId; }
    public String getName() { return name; }
    public String getBreed() { return breed; }
    public int getAge() { return age; }
    public double getPrice() { return price; }
}