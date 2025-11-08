package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.BookingResponse;

import java.util.List;
import java.util.Optional;

public interface BookingReadService {

    Optional<BookingResponse> findById(Long id);
    List<BookingResponse> findAll();
    List<BookingResponse> findByPassengerId(Long passengerId);
    List<BookingResponse> findByDriverId(Long driverId);
}
