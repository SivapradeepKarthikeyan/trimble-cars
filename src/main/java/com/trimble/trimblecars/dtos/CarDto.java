package com.trimble.trimblecars.dtos;


public class CarDto {

    public CarDto() {}

    public CarDto(String carName, String ownerEmail) {
        this.carName = carName;
        this.ownerEmail = ownerEmail;
    }

    private String carName;
    private String ownerEmail;


    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

}
