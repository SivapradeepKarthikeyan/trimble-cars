package com.trimble.trimblecars.dtos;

public class BookCarDto {

    public BookCarDto() {}

    public BookCarDto(String userEmail, String carId, String leaseStartDate, String leaseEndDate) {
        this.userEmail = userEmail;
        this.carId = carId;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
    }

    private String userEmail;
    private String carId;
    private String leaseStartDate;
    private String leaseEndDate;

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

    public String getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public String getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(String leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }
}
