package com.petshop.dto;

public class PetFoodDTO {

    private int foodId;
    private String name;
    private String brand;
    private double price;

    public PetFoodDTO() {
    }

    public PetFoodDTO(int foodId, String name, String brand, double price) {
        this.foodId = foodId;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }


    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}