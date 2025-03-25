package com.trimble.trimblecars.controllers.owner;

import com.trimble.trimblecars.dtos.BookCarDto;
import com.trimble.trimblecars.dtos.CancelCarDto;
import com.trimble.trimblecars.dtos.UserDto;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import com.trimble.trimblecars.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServices userServices;

    @GetMapping("api/v1/user/{email}/car")
    public ResponseEntity<TrimbleCarResponse> getUserCars(@PathVariable("email") String userEmail) {
        log.debug("Received request to get cars for user: {}", userEmail);
        TrimbleCarResponse response = userServices.getUserByEMail(userEmail);

        if (response.getStatusCode() == 200) {
            log.info("Successfully retrieved cars for user: {}", userEmail);
        } else {
            log.warn("Failed to retrieve cars for user: {}, Status: {}",
                    userEmail, response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("api/v1/user")
    public ResponseEntity<TrimbleCarResponse> createUser(@RequestBody UserDto userDto) {
        log.debug("Received request to create user: {}", userDto.getUserEmail());
        TrimbleCarResponse response = userServices.createUser(userDto);

        if (response.getStatusCode() == 201) {
            log.info("Successfully created user: {}", userDto.getUserEmail());
        } else {
            log.warn("Failed to create user: {}, Status: {}",
                    userDto.getUserEmail(), response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("api/v1/user/car")
    public ResponseEntity<TrimbleCarResponse> getCarsByStatus(@RequestParam String status) {
        log.debug("Received request to get cars with status: {}", status);
        TrimbleCarResponse response = userServices.getCarsByStatus(status);

        if (response.getStatusCode() == 200) {
            log.info("Successfully retrieved cars with status: {}", status);
        } else {
            log.warn("Failed to retrieve cars with status: {}, Status: {}",
                    status, response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("api/v1/user/car")
    public ResponseEntity<TrimbleCarResponse> bookCar(@RequestBody BookCarDto bookCarDto) {
        log.debug("Received request to book car for user: {}", bookCarDto.getUserEmail());
        TrimbleCarResponse response = userServices.bookCarToUser(bookCarDto);

        if (response.getStatusCode() == 200) {
            log.info("Successfully booked car for user: {}", bookCarDto.getUserEmail());
        } else {
            log.warn("Failed to book car for user: {}, Status: {}",
                    bookCarDto.getUserEmail(), response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("api/v1/user/car")
    public ResponseEntity<TrimbleCarResponse> cancelCar(@RequestBody CancelCarDto cancelCarDto) {
        log.debug("Received request to cancel car for user: {}", cancelCarDto.getUserEmail());
        TrimbleCarResponse response = userServices.cancelCar(cancelCarDto);

        if (response.getStatusCode() == 200) {
            log.info("Successfully cancelled car for user: {}", cancelCarDto.getUserEmail());
        } else {
            log.warn("Failed to cancel car for user: {}, Status: {}",
                    cancelCarDto.getUserEmail(), response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}