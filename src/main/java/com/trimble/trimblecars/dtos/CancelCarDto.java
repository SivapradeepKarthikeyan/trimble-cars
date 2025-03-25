package com.trimble.trimblecars.dtos;

public class CancelCarDto {


    public CancelCarDto() {}

    public CancelCarDto(String userEmail, String carId) {
        this.userEmail = userEmail;
        this.carId = carId;
    }

    private String userEmail;
    private String carId;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
