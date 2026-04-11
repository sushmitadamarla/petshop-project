package com.petshop.entity;

import jakarta.persistence.*;
import java.util.List;
import com.petshop.entity.Pet;

@Entity
public class GroomingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String serviceName;
    private double price;

    @ManyToMany(mappedBy = "groomingServices")
    private List<Pet> pets;

    public int getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
