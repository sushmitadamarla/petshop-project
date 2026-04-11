package com.petshop.dto;

public class SupplierDTO {
    private int supplierId;
    private String name;
    private String contactPerson;

    public SupplierDTO(int id, String name, String contact) {
        this.supplierId = id;
        this.name = name;
        this.contactPerson = contact;
    }

    // getters

    public int getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}