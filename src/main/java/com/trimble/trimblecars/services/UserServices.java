package com.trimble.trimblecars.services;

import com.trimble.trimblecars.dtos.BookCarDto;
import com.trimble.trimblecars.dtos.CancelCarDto;
import com.trimble.trimblecars.dtos.CarDto;
import com.trimble.trimblecars.dtos.UserDto;
import com.trimble.trimblecars.entities.Car;
import com.trimble.trimblecars.entities.CarLeaseHistory;
import com.trimble.trimblecars.entities.Users;
import com.trimble.trimblecars.repositories.CarLeaseHistoryRepository;
import com.trimble.trimblecars.repositories.CarRepository;
import com.trimble.trimblecars.repositories.UsersRepository;
import com.trimble.trimblecars.responses.TrimbleCarResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServices {
    private static final Logger log = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CarLeaseHistoryRepository carLeaseHistoryRepository;

    public TrimbleCarResponse getUserByEMail(String userEmail) {
        log.debug("Fetching user with email: {}", userEmail);
        try {
            Optional<Users> optional = usersRepository.findByUserEmail(userEmail);
            if (optional.isPresent()) {
                log.info("User found: {}", userEmail);
                return new TrimbleCarResponse("success", "fetch success", 200, optional.get());
            }
            log.warn("User not found: {}", userEmail);
            return new TrimbleCarResponse("not_found", "user not found", 404, null);
        } catch (Exception e) {
            log.error("Error fetching user: {}", userEmail, e);
            return new TrimbleCarResponse("internal_server_error", "fetch failed", 500, null);
        }
    }

    public TrimbleCarResponse createUser(UserDto userDto) {
        log.debug("Creating user: {}", userDto.getUserEmail());
        try {
            Optional<Users> optional = usersRepository.findByUserEmail(userDto.getUserEmail());
            if (optional.isPresent()) {
                log.warn("User already exists: {}", userDto.getUserEmail());
                return new TrimbleCarResponse("conflict", "user already exist", 409, null);
            }

            Users user = new Users(
                    userDto.getUserEmail(),
                    userDto.getUserFirstName(),
                    userDto.getUserLastName()
            );
            Users response = usersRepository.save(user);

            log.info("User created: {}", user.getUserEmail());
            return new TrimbleCarResponse("success", "created successfully", 201, response);
        } catch (Exception e) {
            log.error("Error creating user: {}", userDto.getUserEmail(), e);
            return new TrimbleCarResponse("internal_server_error", "creation failed", 500, null);
        }
    }

    public TrimbleCarResponse getCarsByStatus(String status) {
        log.debug("Fetching cars with status: {}", status);
        try {
            List<Car> carList = carRepository.findByStatus(status);
            log.info("Fetched {} cars with status: {}", carList.size(), status);
            return new TrimbleCarResponse("success", "fetched successfully", 200, carList);
        } catch (Exception e) {
            log.error("Error fetching cars with status: {}", status, e);
            return new TrimbleCarResponse("internal_server_error", "fetch failed", 500, null);
        }
    }

    public TrimbleCarResponse bookCarToUser(BookCarDto bookCarDto) {
        log.debug("Booking car for user: {}", bookCarDto.getUserEmail());
        try {
            Optional<Users> optionalUser = usersRepository.findByUserEmail(bookCarDto.getUserEmail());
            if (optionalUser.isEmpty()) {
                log.warn("User not found for booking: {}", bookCarDto.getUserEmail());
                return new TrimbleCarResponse("failed", "user not found", 404, null);
            }

            Users user = optionalUser.get();
            if (user.getBookedCars().size() >= 2) {
                log.warn("User car limit exceeded: {}", bookCarDto.getUserEmail());
                return new TrimbleCarResponse("failed", "user car limit exceeded", 429, null);
            }

            Optional<Car> optionalCar = carRepository.findById(bookCarDto.getCarId());
            if (optionalCar.isEmpty()) {
                log.warn("Car not found for booking: {}", bookCarDto.getCarId());
                return new TrimbleCarResponse("failed", "car not found", 404, null);
            }

            Car car = optionalCar.get();
            if (car.getStatus().equals("LEASED")) {
                log.warn("Car already leased: {}", bookCarDto.getCarId());
                return new TrimbleCarResponse("failed", "car is already leased", 409, null);
            }

            car.setUser(user);
            car.setUserEmail(bookCarDto.getUserEmail());
            car.setLeaseStartDate(bookCarDto.getLeaseStartDate());
            car.setLeaseEndDate(bookCarDto.getLeaseEndDate());
            car.setStatus("LEASED");

            user.getBookedCars().add(car);

            CarLeaseHistory leaseHistory = new CarLeaseHistory(
                    car, user, bookCarDto.getUserEmail(), car.getCarName(),
                    "BOOKED", bookCarDto.getLeaseStartDate(), bookCarDto.getLeaseEndDate()
            );
            carLeaseHistoryRepository.save(leaseHistory);

            car.getLeaseHistory().add(leaseHistory);
            user.getLeaseHistories().add(leaseHistory);

            log.info("Car booked successfully for user: {}, Car: {}",
                    bookCarDto.getUserEmail(), bookCarDto.getCarId());
            return new TrimbleCarResponse("success", "updated successful", 200, user);
        } catch (Exception e) {
            log.error("Error booking car for user: {}", bookCarDto.getUserEmail(), e);
            return new TrimbleCarResponse("internal_server_error", "updated failed", 500, null);
        }
    }

    public TrimbleCarResponse cancelCar(CancelCarDto cancelCarDto) {
        log.debug("Cancelling car for user: {}", cancelCarDto.getUserEmail());
        try {
            Optional<Car> optionalCar = carRepository.findById(cancelCarDto.getCarId());
            if (optionalCar.isEmpty()) {
                log.warn("Car not found for cancellation: {}", cancelCarDto.getCarId());
                return new TrimbleCarResponse("not_found", "car not found", 404, null);
            }

            Optional<Users> optionalUser = usersRepository.findByUserEmail(cancelCarDto.getUserEmail());
            if (optionalUser.isEmpty()) {
                log.warn("User not found for car cancellation: {}", cancelCarDto.getUserEmail());
                return new TrimbleCarResponse("not_found", "user not found", 404, null);
            }

            Car car = optionalCar.get();
            Users user = optionalUser.get();

            List<Car> userBookedCars = user.getBookedCars();
            boolean removed = userBookedCars.removeIf(car_ -> car_.getCarId().equals(cancelCarDto.getCarId()));

            if (!removed) {
                log.warn("Car not assigned to user for cancellation: {}, {}",
                        cancelCarDto.getUserEmail(), cancelCarDto.getCarId());
                return new TrimbleCarResponse("failed", "Car not assigned to user", 404, null);
            }

            car.setUser(null);
            car.setStatus("FREE");
            car.setUserEmail("");
            car.setLeaseStartDate("00-00-0000");
            car.setLeaseEndDate("00-00-0000");

            CarLeaseHistory carLeaseHistory = new CarLeaseHistory(
                    car, user, cancelCarDto.getUserEmail(), car.getCarName(),
                    "CANCELLED", "00-00-0000", "00-00-0000"
            );
            carLeaseHistoryRepository.save(carLeaseHistory);

            car.getLeaseHistory().add(carLeaseHistory);
            user.getLeaseHistories().add(carLeaseHistory);

            log.info("Car cancelled successfully for user: {}, Car: {}",
                    cancelCarDto.getUserEmail(), cancelCarDto.getCarId());
            return new TrimbleCarResponse("success", "car cancelled successfully", 200, null);
        } catch (Exception e) {
            log.error("Error cancelling car for user: {}", cancelCarDto.getUserEmail(), e);
            return new TrimbleCarResponse("internal_server_error", "deletion failed", 500, null);
        }
    }

    private boolean checkDates(String carLeaseEndDate, String userRequestedStartDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate leaseEndDate = LocalDate.parse(carLeaseEndDate, formatter);
            LocalDate requestedStartDate = LocalDate.parse(userRequestedStartDate, formatter);
            return requestedStartDate.isAfter(leaseEndDate);
        } catch (Exception e) {
            return false;
        }
    }
}