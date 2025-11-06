package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.dto.DriverRequest;
import com.Uber.UberApp.dto.DriverResponse;
import com.Uber.UberApp.mapper.DriverMapper;
import com.Uber.UberApp.model.Driver;
import com.Uber.UberApp.model.DriverElasticDocument;
import com.Uber.UberApp.model.PassengerElasticDocument;
import com.Uber.UberApp.repository.DriverDocumentRepository;
import com.Uber.UberApp.repository.DriverRepository;
import com.Uber.UberApp.service.DriverService;
import com.Uber.UberApp.service.IDriverIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final IDriverIndexService driverIndexService;
    private final DriverDocumentRepository driverDocumentRepository;
    @Override
    @Transactional(readOnly = true)
    public Optional<DriverResponse> findById(Long id) {
        return driverRepository.findById(id)
                .map(DriverMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponse> findAll() {
        return driverRepository.findAll()
                .stream()
                .map(DriverMapper::toResponse)
                .collect(Collectors.toList())
;    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DriverResponse> findByEmail(String email) {
         return driverRepository.findByEmail(email)
                .map(DriverMapper::toResponse);
    }

    @Override
    public List<DriverResponse> findAvailableDrivers() {
        return driverRepository.findAll()
                .stream()
                .filter(Driver::getIsAvailable)
                .map(DriverMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DriverResponse create(DriverRequest request) {
        if(driverRepository.existsByEmail(request.getEmail())){
            throw  new IllegalArgumentException("Driver with email"+request.getEmail()+"already exists");

        }
        if(driverRepository.existsByLicenseNumber(request.getLicenseNumber())){
            throw  new IllegalArgumentException("Driver with license number"+request.getLicenseNumber()+"already exists");

        }
        Driver driver=DriverMapper.toEntity(request);
        Driver savedDriver=driverRepository.save(driver);
        driverIndexService.createDriverIndex(driver);
        return DriverMapper.toResponse(savedDriver);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverElasticDocument> findByElasticSearch(String query) {

        return driverDocumentRepository.findByNameContainingOrEmailContainingOrLicenseNumberContaining(query,query,query
        );

    }

    @Override
    public DriverResponse update(Long id, DriverRequest request) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + id));

        if(!driver.getEmail().equals(request.getEmail())
        && driverRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Driver with email " + request.getEmail() + " already exists");
        }

        if (!driver.getLicenseNumber().equals(request.getLicenseNumber()) &&
                driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new IllegalArgumentException("Driver with license number " + request.getLicenseNumber() + " already exists");
        }

        DriverMapper.updateEntity(driver, request);
        Driver updatedDriver = driverRepository.save(driver);
        return DriverMapper.toResponse(updatedDriver);

    }

    @Override
    public void deleteById(Long id) {
        if(!driverRepository.existsById(id)){
            throw new IllegalArgumentException("DriverId not found");
        }
        driverRepository.deleteById(id);

    }
}
