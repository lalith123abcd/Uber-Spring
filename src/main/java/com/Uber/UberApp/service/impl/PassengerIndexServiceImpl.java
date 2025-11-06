package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.model.Passenger;
import com.Uber.UberApp.model.PassengerElasticDocument;
import com.Uber.UberApp.repository.PassengerDocumentRepository;
import com.Uber.UberApp.service.IPassengerIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerIndexServiceImpl implements IPassengerIndexService {

    private final PassengerDocumentRepository passengerDocumentRepository;
    @Override
    public void createPassengerIndex(Passenger passenger) {

        PassengerElasticDocument document=PassengerElasticDocument.builder()
                .id(String.valueOf(passenger.getId()))
                .name(passenger.getName())
                .email(passenger.getEmail())
                .build();

        passengerDocumentRepository.save(document);


    }
}
