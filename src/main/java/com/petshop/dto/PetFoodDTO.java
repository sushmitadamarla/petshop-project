package com.petshop.dto;

public class PetFoodDTO {
    private int foodId;
    private String name;
    private String brand;
    private double price;

    public PetFoodDTO(int id, String name, String brand, double price) {
        this.foodId = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    // getters


    public int getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }
}