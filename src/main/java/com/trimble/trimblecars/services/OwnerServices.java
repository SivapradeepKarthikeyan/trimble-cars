package com.trimble.trimblecars.services;

import com.trimble.trimblecars.dtos.CarDto;
import com.trimble.trimblecars.dtos.OwnerDto;
import com.trimble.trimblecars.entities.Car;
import com.trimble.trimblecars.entities.Owner;
import com.trimble.trimblecars.repositories.CarRepository;
import com.trimble.trimblecars.repositories.OwnerRepository;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class OwnerServices {
    private static final Logger log = LoggerFactory.getLogger(OwnerServices.class);

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    CarRepository carRepository;

    public TrimbleCarResponse getOwnerByEmail(String ownerEmail) {
        log.debug("Fetching owner with email: {}", ownerEmail);
        try {
            Optional<Owner> optional = ownerRepository.findByOwnerEmail(ownerEmail);
            if (optional.isPresent()) {
                log.info("Owner found: {}", ownerEmail);
                return new TrimbleCarResponse("success", "fetched successfully", 200, optional.get());
            }
            log.warn("Owner not found: {}", ownerEmail);
            return new TrimbleCarResponse("not_found", "owner not found", 404, null);
        } catch (Exception e) {
            log.error("Error fetching owner: {}", ownerEmail, e);
            return new TrimbleCarResponse("internal_server_error", "fetch failed", 500, null);
        }
    }

    public TrimbleCarResponse createOwner(OwnerDto ownerDto) {
        log.debug("Creating owner: {}", ownerDto.getOwnerEmail());
        try {
            Optional<Owner> optional = ownerRepository.findByOwnerEmail(ownerDto.getOwnerEmail());
            if (optional.isPresent()) {
                log.warn("Owner already exists: {}", ownerDto.getOwnerEmail());
                return new TrimbleCarResponse("conflict", "owner already present", 409, null);
            }

            Owner owner = new Owner(
                    ownerDto.getOwnerEmail(),
                    ownerDto.getOwnerFirstName(),
                    ownerDto.getOwnerLastName()
            );
            Owner response = ownerRepository.save(owner);

            log.info("Owner created: {}", owner.getOwnerEmail());
            return new TrimbleCarResponse("success", "created successfully", 201, response);
        } catch (Exception e) {
            log.error("Error creating owner: {}", ownerDto.getOwnerEmail(), e);
            return new TrimbleCarResponse("internal_server_error", "creation failed", 500, null);
        }
    }

    public TrimbleCarResponse createCar(CarDto carDto) {
        log.debug("Creating car for owner: {}", carDto.getOwnerEmail());
        try {
            Optional<Owner> optional = ownerRepository.findByOwnerEmail(carDto.getOwnerEmail());

            if (optional.isPresent()) {
                Owner owner = optional.get();
                Car car = new Car(
                        carDto.getCarName(),
                        owner,
                        carDto.getOwnerEmail(),
                        "FREE",
                        "00-00-0000",
                        "00-00-0000"
                );

                Car response = carRepository.save(car);

                log.info("Car created: {} for owner: {}", car.getCarName(), carDto.getOwnerEmail());

                return new TrimbleCarResponse("success", "created successfully", 201, response);
            } else {
                log.warn("Owner not found for car creation: {}", carDto.getOwnerEmail());
                return new TrimbleCarResponse("not_found", "owner not found", 404, null);
            }
        } catch (Exception e) {
            log.error("Error creating car for owner: {}", carDto.getOwnerEmail(), e);
            return new TrimbleCarResponse("internal_server_error", "creation failed", 500, null);
        }
    }
}