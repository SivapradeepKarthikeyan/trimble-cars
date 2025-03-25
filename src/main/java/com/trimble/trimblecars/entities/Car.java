package com.trimble.trimblecars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Car {

    public Car() {}

    public Car(String carName, Owner owner, String ownerEmail, String status, String leaseStartDate, String leaseEndDate) {
        this.carName = carName;
        this.owner = owner;
        this.ownerEmail = ownerEmail;
        this.status = status;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String carId;
    private String carName;

    //Here if we give an owner object as an arg via constructor
    //Hibernate will automatically assign the owner id with this car.
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Owner owner;
    private String ownerEmail;

    //Here if we give a user object as an arg via constructor
    //Hibernate will automatically assign the user id with this car.
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;
    private String userEmail;

    //FREE (or) LEASED
    private String status;
    private String leaseStartDate;
    private String leaseEndDate;

    //One car can have multiple lease history
    @OneToMany(mappedBy = "car")
    List<CarLeaseHistory> carLeaseHistory;


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<CarLeaseHistory> getLeaseHistory() {
        return carLeaseHistory;
    }

    public void setLeaseHistory(List<CarLeaseHistory> leaseHistory) {
        this.carLeaseHistory = leaseHistory;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId='" + carId + '\'' +
                ", carName='" + carName + '\'' +
                ", owner=" + owner +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", user=" + user +
                ", userEmail='" + userEmail + '\'' +
                ", status='" + status + '\'' +
                ", leaseStartDate='" + leaseStartDate + '\'' +
                ", leaseEndDate='" + leaseEndDate + '\'' +
                ", leaseHistory=" + carLeaseHistory +
                '}';
    }
}
