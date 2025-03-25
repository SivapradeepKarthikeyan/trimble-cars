package com.trimble.trimblecars.controllers.owner;

import com.trimble.trimblecars.dtos.*;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import com.trimble.trimblecars.services.OwnerServices;
import com.trimble.trimblecars.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private OwnerServices ownerServices;

    @Autowired
    private UserServices userServices;

    // Owner-related Operations
    @PostMapping("/owner")
    public ResponseEntity<TrimbleCarResponse> createOwner(@RequestBody OwnerDto ownerDto) {
        logger.info("Admin creating owner: {}", ownerDto);
        TrimbleCarResponse response = ownerServices.createOwner(ownerDto);
        logger.info("Owner created with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/owner/{ownerEmail}/car")
    public ResponseEntity<TrimbleCarResponse> getOwnerCarsByEmail(@PathVariable String ownerEmail) {
        logger.info("Admin fetching cars for owner: {}", ownerEmail);
        TrimbleCarResponse response = ownerServices.getOwnerByEmail(ownerEmail);
        logger.info("Fetched owner cars with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/car")
    public ResponseEntity<TrimbleCarResponse> createCar(@RequestBody CarDto carDto) {
        logger.info("Admin registering a new car: {}", carDto);
        TrimbleCarResponse response = ownerServices.createCar(carDto);
        logger.info("Car registered with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // User-related Operations
    @PostMapping("/user")
    public ResponseEntity<TrimbleCarResponse> createUser(@RequestBody UserDto userDto) {
        logger.info("Admin creating user: {}", userDto);
        TrimbleCarResponse response = userServices.createUser(userDto);
        logger.info("User created with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/user/{email}/car")
    public ResponseEntity<TrimbleCarResponse> getUserCars(@PathVariable("email") String userEmail) {
        logger.info("Admin fetching cars for user: {}", userEmail);
        TrimbleCarResponse response = userServices.getUserByEMail(userEmail);
        logger.info("Fetched user cars with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/car")
    public ResponseEntity<TrimbleCarResponse> getCarsByStatus(@RequestParam String status) {
        logger.info("Admin fetching cars by status: {}", status);
        TrimbleCarResponse response = userServices.getCarsByStatus(status);
        logger.info("Fetched cars with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/user/car")
    public ResponseEntity<TrimbleCarResponse> bookCar(@RequestBody BookCarDto bookCarDto) {
        logger.info("Admin booking car: {}", bookCarDto);
        TrimbleCarResponse response = userServices.bookCarToUser(bookCarDto);
        logger.info("Car booked with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/user/car")
    public ResponseEntity<TrimbleCarResponse> cancelCar(@RequestBody CancelCarDto cancelCarDto) {
        logger.info("Admin canceling car lease: {}", cancelCarDto);
        TrimbleCarResponse response = userServices.cancelCar(cancelCarDto);
        logger.info("Car lease canceled with status: {}", response.getStatusCode());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
