package com.trimble.trimblecars.dtos;

public class OwnerDto {

    public OwnerDto() {}

    public OwnerDto(String ownerEmail, String ownerFirstName, String ownerLastName) {
        this.ownerEmail = ownerEmail;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }

    private String ownerEmail;
    private String ownerFirstName;
    private String ownerLastName;


    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }
}
