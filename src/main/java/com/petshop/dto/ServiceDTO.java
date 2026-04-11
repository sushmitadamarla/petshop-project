package com.petshop.dto;

public class ServiceDTO {
    private int id;
    private String name;

    public ServiceDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
