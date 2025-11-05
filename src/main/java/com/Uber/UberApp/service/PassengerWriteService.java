package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.PassengerRequest;
import com.Uber.UberApp.dto.PassengerResponse;

public interface PassengerWriteService {

    PassengerResponse createPassenger(PassengerRequest passengerRequest);

    PassengerResponse updatePassenger(Long id,PassengerRequest passengerRequest);

    void deleteById(Long id);
}
