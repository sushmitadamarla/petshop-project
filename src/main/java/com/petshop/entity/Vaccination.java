package com.petshop.entity;

import jakarta.persistence.*;
import java.util.List;
import com.petshop.entity.Pet;

@Entity
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vaccineName;
    private String date;

    @ManyToMany(mappedBy = "vaccinations")
    private List<Pet> pets;

    // getters & setters


    public int getId() {
        return id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getDate() {
        return date;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}