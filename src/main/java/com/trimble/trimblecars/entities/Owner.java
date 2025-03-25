package com.trimble.trimblecars.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Owner {

    public Owner() {}

    public Owner( String ownerEmail,String ownerFirstName,String ownerLastName) {
        this.ownerEmail = ownerEmail;
        this.ownerFirstName=ownerFirstName;
        this.ownerLastName=ownerLastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ownerId;
    private String ownerEmail;
    private String ownerFirstName;
    private String ownerLastName;

    //One owner can have many cars
    //Each car will be mapped by owner ID
    //If we give the owner object to car it will automatically map that car to this user id
    @OneToMany(mappedBy = "owner" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Car> cars;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerFirstName() {return ownerFirstName;}

    public void setOwnerFirstName(String ownerFirstName) {this.ownerFirstName = ownerFirstName;}

    public String getOwnerLastName() {return ownerLastName;}

    public void setOwnerLastName(String ownerLastName) {this.ownerLastName = ownerLastName;}

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId='" + ownerId + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", ownerFirstName='" + ownerFirstName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", cars=" + cars +
                '}';
    }
}
