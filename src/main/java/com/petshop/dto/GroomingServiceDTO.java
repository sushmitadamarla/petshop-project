package com.petshop.dto;

public class GroomingServiceDTO {

    private int serviceId;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public GroomingServiceDTO(int serviceId, String name, String description, double price, boolean available) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}