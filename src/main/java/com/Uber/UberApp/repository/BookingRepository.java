package com.Uber.UberApp.repository;

import com.Uber.UberApp.model.Booking;
import com.Uber.UberApp.model.Driver;
import com.Uber.UberApp.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByPassenger(Passenger passenger);
    List<Booking> findByDriver(Driver driver);
    Optional<Booking> findByIdAndPassenger(Long id, Passenger passenger);
    Optional<Booking> findByIdAndDriver(Long id, Driver driver);
}
