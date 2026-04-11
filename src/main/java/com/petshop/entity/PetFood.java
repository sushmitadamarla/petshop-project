package com.petshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petshop.entity.Pet;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class PetFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodId;

    private String name;
    private String brand;
    private String type;
    private int quantity;
    private double price;

    @ManyToMany(mappedBy = "foods")
    @JsonIgnore
    private Set<Pet> pets;

    // getters/setters
    public int getFoodId() { return foodId; }
    public void setFoodId(int foodId) { this.foodId = foodId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}