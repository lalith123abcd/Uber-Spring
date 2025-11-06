package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.DriverResponse;
import com.Uber.UberApp.model.DriverElasticDocument;
import com.Uber.UberApp.model.PassengerElasticDocument;

import java.util.List;
import java.util.Optional;

public interface DriverReadService {

    Optional<DriverResponse> findById(Long id);
    List<DriverResponse> findAll();
    Optional<DriverResponse> findByEmail(String email);
    List<DriverResponse> findAvailableDrivers();
     List<DriverElasticDocument> findByElasticSearch(String query);
}
