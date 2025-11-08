package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.BookingRequest;
import com.Uber.UberApp.dto.BookingResponse;
import com.Uber.UberApp.model.Booking;

public interface BookingWriteService {

    BookingResponse create(BookingRequest request);
    BookingResponse update(Long id, BookingRequest request);
    BookingResponse updateStatus(Long id, Booking.BookingStatus status);
    void deleteById(Long id);
}
