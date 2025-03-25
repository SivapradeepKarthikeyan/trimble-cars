package com.trimble.trimblecars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class CarLeaseHistory {

    public CarLeaseHistory() {}

    public CarLeaseHistory(Car car, Users user, String userEmail ,String carName,String status,String startDate, String endDate) {
        this.car = car;
        this.user = user;
        this.userEmail=userEmail;
        this.carName=carName;
        this.status=status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;

    private String userEmail;
    private String carName;
    private String status;
    private String startDate;
    private String endDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CarLeaseHistory{" +
                "id='" + id + '\'' +
                ", car=" + car +
                ", user=" + user +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
