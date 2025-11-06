package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.dto.PassengerRequest;
import com.Uber.UberApp.dto.PassengerResponse;
import com.Uber.UberApp.mapper.PassengerMapper;
import com.Uber.UberApp.model.Passenger;
import com.Uber.UberApp.model.PassengerElasticDocument;
import com.Uber.UberApp.repository.PassengerDocumentRepository;
import com.Uber.UberApp.repository.PassengerRepository;
import com.Uber.UberApp.service.IPassengerIndexService;
import com.Uber.UberApp.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    private  final IPassengerIndexService passengerIndexService;
    private final PassengerDocumentRepository passengerDocumentRepository;
    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerResponse> findById(Long id) {
        return passengerRepository.findById(id).
                map(PassengerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponse> findAll() {
        return passengerRepository.findAll()
                .stream()
                .map(PassengerMapper::toResponse)
                .collect(Collectors.toList());


    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerResponse> findByEmail(String email) {
        return passengerRepository.findByEmail(email)
                .map(PassengerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerElasticDocument> findByElasticSearch(String query) {

        return passengerDocumentRepository.findByNameContainingOrEmailContaining(query,query);

    }

    @Override
    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        if(passengerRepository.existsByEmail(passengerRequest.getEmail())){
            throw  new IllegalArgumentException("Passenger with email "+passengerRequest.getEmail()+"already exists");

        }
        Passenger passenger= PassengerMapper.toEntity(passengerRequest);
        Passenger savedPassenger=passengerRepository.save(passenger);

        passengerIndexService.createPassengerIndex(passenger);
        return PassengerMapper.toResponse(savedPassenger);
    }

    @Override
    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {
        Passenger passenger=passengerRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Passenger not found with id "+id));

        if(!passenger.getEmail().equals(passengerRequest.getEmail())&&
        passengerRepository.existsByEmail(passengerRequest.getEmail())){
            throw new IllegalArgumentException("passenger with this new email id is already present");

        }

        PassengerMapper.updateEntity(passenger,passengerRequest);
        Passenger updatedPassenger=passengerRepository.save(passenger);
        return PassengerMapper.toResponse(updatedPassenger);
    }

    @Override
    public void deleteById(Long id) {
        if(!passengerRepository.existsById(id)){
            throw new IllegalArgumentException("Passenger not found to delete");

        }

        passengerRepository.deleteById(id);

    }
}
