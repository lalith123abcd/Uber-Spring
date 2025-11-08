package com.Uber.UberApp.mapper;

import com.Uber.UberApp.dto.BookingRequest;
import com.Uber.UberApp.dto.BookingResponse;
import com.Uber.UberApp.model.Booking;
import com.Uber.UberApp.model.Driver;
import com.Uber.UberApp.model.Passenger;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class BookingMapper {

    public Booking toEntity(BookingRequest request, Passenger passenger, Driver driver) {
        Booking.BookingStatus status = driver != null ? Booking.BookingStatus.CONFIRMED : Booking.BookingStatus.PENDING;

        return Booking.builder()
                .passenger(passenger)
                .driver(driver)
                .pickUpLocation(request.getPickUpLocation())
                .dropOffLocation(request.getDropOffLocation())
                .fare(request.getFare())
                .status(status)
                .scheduledPickupTime(request.getScheduledPickupTime())
                .build();
    }

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .passengerId(booking.getPassenger() != null ? booking.getPassenger().getId() : null)
                .passengerName(booking.getPassenger() != null ? booking.getPassenger().getName() : null)
                .driverId(booking.getDriver() != null ? booking.getDriver().getId() : null)
                .driverName(booking.getDriver() != null ? booking.getDriver().getName() : null)
                .pickUpLocation(booking.getPickUpLocation())
                .dropOffLocation(booking.getDropOffLocation())
                .status(booking.getStatus())
                .fare(booking.getFare())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .scheduledPickupTime(booking.getScheduledPickupTime())
                .actualPickupTime(booking.getActualPickupTime())
                .completedAt(booking.getCompletedAt())
                .build();
    }


    public void updateEntity(Booking booking, BookingRequest request, Passenger passenger, Driver driver) {
        booking.setPassenger(passenger);
        booking.setDriver(driver);
        booking.setPickUpLocation(request.getPickUpLocation());
        booking.setDropOffLocation(request.getDropOffLocation());
        booking.setFare(request.getFare());
        booking.setScheduledPickupTime(request.getScheduledPickupTime());

        // Update status based on driver assignment
        if (driver != null && booking.getStatus() == Booking.BookingStatus.PENDING) {
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
        }
    }


}
