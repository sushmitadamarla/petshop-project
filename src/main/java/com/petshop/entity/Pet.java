package com.petshop.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private int petId;

    private String name;
    private String breed;
    private int age;
    private double price;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PetCategory category;

    @ManyToMany
    @JoinTable(
            name = "pet_grooming_relationship",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<GroomingService> groomingServices = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "pet_vaccination_relationship",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccination_id")
    )
    private Set<Vaccination> vaccinations = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "employee_pet_relationship",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "pet_food_relationship",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<PetFood> foods = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "pet_supplier_relationship",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private Set<Supplier> suppliers = new HashSet<>();

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public PetCategory getCategory() { return category; }
    public void setCategory(PetCategory category) { this.category = category; }

    public Set<GroomingService> getGroomingServices() { return groomingServices; }
    public void setGroomingServices(Set<GroomingService> groomingServices) { this.groomingServices = groomingServices; }

    public Set<Vaccination> getVaccinations() { return vaccinations; }
    public void setVaccinations(Set<Vaccination> vaccinations) { this.vaccinations = vaccinations; }

    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }

    public Set<PetFood> getFoods() { return foods; }
    public void setFoods(Set<PetFood> foods) { this.foods = foods; }

    public Set<Supplier> getSuppliers() { return suppliers; }
    public void setSuppliers(Set<Supplier> suppliers) { this.suppliers = suppliers; }
    
    
}