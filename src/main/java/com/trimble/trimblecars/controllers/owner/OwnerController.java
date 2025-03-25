package com.trimble.trimblecars.controllers.owner;

import com.trimble.trimblecars.dtos.CarDto;
import com.trimble.trimblecars.dtos.OwnerDto;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import com.trimble.trimblecars.services.OwnerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OwnerController {
    private static final Logger log = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    OwnerServices ownerServices;

    @GetMapping("/api/v1/owner/{ownerEmail}/car")
    public ResponseEntity<TrimbleCarResponse> getOwnerCarsByEmail(@PathVariable String ownerEmail) {
        log.debug("Received request to get cars for owner: {}", ownerEmail);
        TrimbleCarResponse response = ownerServices.getOwnerByEmail(ownerEmail);

        if (response.getStatusCode() == 200) {
            log.info("Successfully retrieved cars for owner: {}", ownerEmail);
        } else {
            log.warn("Failed to retrieve cars for owner: {}, Status: {}",
                    ownerEmail, response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("api/v1/owner")
    public ResponseEntity<TrimbleCarResponse> createOwner(@RequestBody OwnerDto ownerDto) {
        log.debug("Received request to create owner: {}", ownerDto.getOwnerEmail());
        TrimbleCarResponse response = ownerServices.createOwner(ownerDto);

        if (response.getStatusCode() == 201) {
            log.info("Successfully created owner: {}", ownerDto.getOwnerEmail());
        } else {
            log.warn("Failed to create owner: {}, Status: {}",
                    ownerDto.getOwnerEmail(), response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("api/v1/owner/car")
    public ResponseEntity<TrimbleCarResponse> createCar(@RequestBody CarDto carDto) {
        log.debug("Received request to create car for owner: {}", carDto.getOwnerEmail());
        TrimbleCarResponse response = ownerServices.createCar(carDto);

        if (response.getStatusCode() == 201) {
            log.info("Successfully created car for owner: {}", carDto.getOwnerEmail());
        } else {
            log.warn("Failed to create car for owner: {}, Status: {}",
                    carDto.getOwnerEmail(), response.getStatusCode());
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}