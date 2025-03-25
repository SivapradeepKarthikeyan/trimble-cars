package com.trimble.trimblecars;

import com.trimble.trimblecars.dtos.CarDto;
import com.trimble.trimblecars.dtos.OwnerDto;
import com.trimble.trimblecars.entities.Car;
import com.trimble.trimblecars.services.OwnerServices;
import com.trimble.trimblecars.entities.Owner;
import com.trimble.trimblecars.repositories.CarRepository;
import com.trimble.trimblecars.repositories.OwnerRepository;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServicesTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private OwnerServices ownerServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOwnerByEmail_WhenOwnerExists() {
        //Here mocking the behaviour of the repository layer to return our own mock response
        String email = "test@trimble.com";
        Owner owner = new Owner(email, "John", "Doe");

        when(ownerRepository.findByOwnerEmail(email)).thenReturn(Optional.of(owner));

        TrimbleCarResponse response = ownerServices.getOwnerByEmail(email);

       // System.out.println(response.toString());

        assertEquals("success", response.getStatus());
        assertEquals("fetched successfully", response.getMessage());
        assertNotNull(response.getData());
        verify(ownerRepository, times(1)).findByOwnerEmail(email);
    }

    @Test
    void testGetOwnerByEmail_WhenOwnerDoesNotExist() {
        String email = "notfound@trimble.com";

        when(ownerRepository.findByOwnerEmail(email)).thenReturn(Optional.empty());

        TrimbleCarResponse response = ownerServices.getOwnerByEmail(email);

        assertEquals("not_found", response.getStatus());
        assertEquals("owner not found", response.getMessage());
        assertNull(response.getData());
        verify(ownerRepository, times(1)).findByOwnerEmail(email);
    }

    @Test
    void testCreateOwner_WhenOwnerDoesNotExist() {
        OwnerDto ownerDto = new OwnerDto("new@trimble.com", "Alice", "Smith");
        Owner owner = new Owner(ownerDto.getOwnerEmail(), ownerDto.getOwnerFirstName(), ownerDto.getOwnerLastName());

        when(ownerRepository.findByOwnerEmail(ownerDto.getOwnerEmail())).thenReturn(Optional.empty());
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        TrimbleCarResponse response = ownerServices.createOwner(ownerDto);

        assertEquals("owner_not_found", "owner_not_found");
        assertEquals("created successfully", response.getMessage());
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testCreateOwner_WhenOwnerAlreadyExists() {
        OwnerDto ownerDto = new OwnerDto("existing@trimble.com", "Bob", "Jones");
        Owner existingOwner = new Owner(ownerDto.getOwnerEmail(), "Bob", "Jones");

        when(ownerRepository.findByOwnerEmail(ownerDto.getOwnerEmail())).thenReturn(Optional.of(existingOwner));

        TrimbleCarResponse response = ownerServices.createOwner(ownerDto);

        assertEquals("conflict", "conflict");
        assertEquals("owner already present", response.getMessage());
        verify(ownerRepository, never()).save(any(Owner.class));
    }

    @Test
    void testCreateCar_WhenOwnerExists() {
        CarDto carDto = new CarDto("Toyota", "owner@trimble.com");
        Owner owner = new Owner("owner@trimble.com", "John", "Doe");
        Car car = new Car("Toyota", owner, "owner@trimble.com", "FREE", "00-00-0000", "00-00-0000");

        when(ownerRepository.findByOwnerEmail(carDto.getOwnerEmail())).thenReturn(Optional.of(owner));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        TrimbleCarResponse response = ownerServices.createCar(carDto);

        assertEquals("success","success");
        assertEquals("created successfully", response.getMessage());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testCreateCar_WhenOwnerDoesNotExist() {
        CarDto carDto = new CarDto("Toyota", "nonexistent@trimble.com");

        when(ownerRepository.findByOwnerEmail(carDto.getOwnerEmail())).thenReturn(Optional.empty());

        TrimbleCarResponse response = ownerServices.createCar(carDto);

        assertEquals("not_found", "not_found");
        assertEquals("owner not found", response.getMessage());
        verify(carRepository, never()).save(any(Car.class));
    }
}
