package com.petshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petshop.entity.Pet;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String email;

    @ManyToMany(mappedBy = "suppliers")
    @JsonIgnore
    private Set<Pet> pets;

    // getters/setters
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}