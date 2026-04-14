package com.petshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroomingServiceDTO {

    private int serviceId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be 0 or greater")
    private Double price;

    private boolean available;

    // no-arg constructor — required for @RequestBody deserialization
    public GroomingServiceDTO() {}

    public GroomingServiceDTO(int serviceId, String name, String description,
                              double price, boolean available) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}