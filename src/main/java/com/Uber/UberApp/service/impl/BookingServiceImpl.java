package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.dto.BookingRequest;
import com.Uber.UberApp.dto.BookingResponse;
import com.Uber.UberApp.dto.DriverLocationDTO;
import com.Uber.UberApp.mapper.BookingMapper;
import com.Uber.UberApp.model.Booking;
import com.Uber.UberApp.model.Driver;
import com.Uber.UberApp.model.Passenger;
import com.Uber.UberApp.repository.BookingRepository;
import com.Uber.UberApp.repository.DriverRepository;
import com.Uber.UberApp.repository.PassengerRepository;
import com.Uber.UberApp.service.BookingService;
import com.Uber.UberApp.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final LocationService locationService;
    @Override
    @Transactional(readOnly = true)
    public Optional<BookingResponse> findById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toResponse);
    }

    @Override
    public List<BookingResponse> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> findByPassengerId(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: " + passengerId));
        return bookingRepository.findByPassenger(passenger).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> findByDriverId(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + driverId));
        return bookingRepository.findByDriver(driver).stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse create(BookingRequest request) {

        Passenger passenger=passengerRepository.findById(request.getPassengerId())
                .orElseThrow(()->new IllegalArgumentException("Passenger not found with id"+request.getPassengerId()));

        Booking newBooking= Booking.builder()
                .passenger(passenger)
                .pickUpLocationLatitude(request.getPickUpLocationLatitude())
                .pickUpLocationLongitude(request.getPickUpLocationLongitude())
                .status(Booking.BookingStatus.PENDING)
                .build();

        List<DriverLocationDTO> getNearByDrivers=locationService.getNearbyDrivers(request.getPickUpLocationLatitude(), request.getPickUpLocationLongitude(), 10.0);




        }



    @Override
    public BookingResponse update(Long id, BookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: " + request.getPassengerId()));

        Driver driver = null;
        if (request.getDriverId() != null) {
            driver = driverRepository.findById(request.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + request.getDriverId()));
        }

        // Handle driver availability when updating
        Driver previousDriver = booking.getDriver();
        if (previousDriver != null && !previousDriver.equals(driver)) {
            previousDriver.setIsAvailable(true);
            driverRepository.save(previousDriver);
        }

        if (driver != null && !driver.equals(previousDriver)) {
            if (!driver.getIsAvailable()) {
                throw new IllegalArgumentException("Driver with id " + request.getDriverId() + " is not available");
            }
            driver.setIsAvailable(false);
            driverRepository.save(driver);
        }

        bookingMapper.updateEntity(booking, request, passenger, driver);
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }


    @Override
    public BookingResponse updateStatus(Long id, Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        booking.setStatus(status);

        if(status==Booking.BookingStatus.IN_PROGRESS && booking.getActualPickupTime()==null) {
            booking.setActualPickupTime(LocalDateTime.now());

        }else if(status==Booking.BookingStatus.COMPLETED) {
            if (booking.getDriver() != null) {
                Driver driver = booking.getDriver();
                driver.setIsAvailable(true);
                driverRepository.save(driver
                );
            }} else if (status == Booking.BookingStatus.CANCELLED) {
                if (booking.getDriver() != null) {
                    Driver driver = booking.getDriver();
                    driver.setIsAvailable(true);
                    driverRepository.save(driver
                    );
                }
            }
            Booking updatedBooking = bookingRepository.save(booking);
            return bookingMapper.toResponse(updatedBooking);


    }

    @Override
    public void deleteById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with id: " + id));

        // Release driver if assigned
        if (booking.getDriver() != null) {
            Driver driver = booking.getDriver();
            driver.setIsAvailable(true);
            driverRepository.save(driver);
        }

        bookingRepository.deleteById(id);
    }
}
