package com.trimble.trimblecars;

import com.trimble.trimblecars.dtos.BookCarDto;
import com.trimble.trimblecars.dtos.UserDto;
import com.trimble.trimblecars.entities.Car;
import com.trimble.trimblecars.entities.Users;
import com.trimble.trimblecars.repositories.CarLeaseHistoryRepository;
import com.trimble.trimblecars.repositories.CarRepository;
import com.trimble.trimblecars.repositories.UsersRepository;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import com.trimble.trimblecars.services.UserServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicesTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarLeaseHistoryRepository carLeaseHistoryRepository;

    @InjectMocks
    private UserServices userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByEmail_WhenUserExists() {
        String email = "user@trimble.com";
        Users user = new Users(email, "John", "Doe");

        when(usersRepository.findByUserEmail(email)).thenReturn(Optional.of(user));

        TrimbleCarResponse response = userServices.getUserByEMail(email);

        assertEquals("success", response.getStatus());
        assertEquals("fetch success", response.getMessage());
        assertNotNull(response.getData());
        verify(usersRepository, times(1)).findByUserEmail(email);
    }

    @Test
    void testGetUserByEmail_WhenUserDoesNotExist() {
        String email = "notfound@trimble.com";

        when(usersRepository.findByUserEmail(email)).thenReturn(Optional.empty());

        TrimbleCarResponse response = userServices.getUserByEMail(email);

        assertEquals("not_found", response.getStatus());
        assertEquals("user not found", response.getMessage());
        assertNull(response.getData());
        verify(usersRepository, times(1)).findByUserEmail(email);
    }

    @Test
    void testCreateUser_WhenUserDoesNotExist() {
        UserDto userDto = new UserDto("new@trimble.com", "Alice", "Smith");
        Users user = new Users(userDto.getUserEmail(), userDto.getUserFirstName(), userDto.getUserLastName());

        when(usersRepository.findByUserEmail(userDto.getUserEmail())).thenReturn(Optional.empty());
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        TrimbleCarResponse response = userServices.createUser(userDto);

        assertEquals("success", response.getStatus());
        assertEquals("created successfully", response.getMessage());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testCreateUser_WhenUserAlreadyExists() {
        UserDto userDto = new UserDto("existing@trimble.com", "Bob", "Jones");
        Users existingUser = new Users(userDto.getUserEmail(), "Bob", "Jones");

        when(usersRepository.findByUserEmail(userDto.getUserEmail())).thenReturn(Optional.of(existingUser));

        TrimbleCarResponse response = userServices.createUser(userDto);

        assertEquals("conflict", response.getStatus());
        assertEquals("user already exist", response.getMessage());
        verify(usersRepository, never()).save(any(Users.class));
    }

    @Test
    void testGetCarsByStatus_WhenCarsExist() {
        String status = "AVAILABLE";
        List<Car> cars = List.of(
                new Car("Toyota", null, null, status, null, null),
                new Car("Honda", null, null, status, null, null)
        );

        when(carRepository.findByStatus(status)).thenReturn(cars);

        TrimbleCarResponse response = userServices.getCarsByStatus(status);

        assertEquals("success", response.getStatus());
        assertEquals("fetched successfully", response.getMessage());
        assertEquals(2, ((List<Car>) response.getData()).size());
        verify(carRepository, times(1)).findByStatus(status);
    }

}
