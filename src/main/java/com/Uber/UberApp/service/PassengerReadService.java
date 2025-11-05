package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.PassengerResponse;

import java.util.List;
import java.util.Optional;

public interface PassengerReadService {

    Optional<PassengerResponse> findById(Long id);
    List<PassengerResponse> findAll();
    Optional<PassengerResponse> findByEmail(String email);
}
