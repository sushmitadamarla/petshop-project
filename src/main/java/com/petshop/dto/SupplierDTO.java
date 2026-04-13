package com.petshop.dto;

public class SupplierDTO {

    private int supplierId;
    private String name;
    private String contactPerson;


    public SupplierDTO() {
    }


    public SupplierDTO(int supplierId, String name, String contactPerson) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactPerson = contactPerson;
    }

    // Getters and Setters

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}