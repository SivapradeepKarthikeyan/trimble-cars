package com.trimble.trimblecars.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Users {

    public Users() {}

    public Users(String userEmail , String userFirstName , String userLastName) {
        this.userEmail = userEmail;
        this.userFirstName=userFirstName;
        this.userLastName=userLastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;

    //One user can have multiple cars can be booked
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Car> bookedCars;

    //One user can have multiple lease history
    @OneToMany(mappedBy = "user")
    private List<CarLeaseHistory> userLeaseHistory;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {return userFirstName;}

    public void setUserFirstName(String userFirstName) {this.userFirstName = userFirstName;}

    public String getUserLastName() {return userLastName;}

    public void setUserLastName(String userLastName) {this.userLastName = userLastName;}

    public List<Car> getBookedCars() {
        return bookedCars;
    }

    public void setBookedCars(List<Car> bookedCars) {
        this.bookedCars = bookedCars;
    }

    public List<CarLeaseHistory> getLeaseHistories() {
        return userLeaseHistory;
    }

    public void setLeaseHistories(List<CarLeaseHistory> leaseHistories) {
        this.userLeaseHistory = leaseHistories;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", bookedCars=" + bookedCars +
                ", leaseHistories=" + userLeaseHistory +
                '}';
    }
}
