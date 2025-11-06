package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.model.Driver;
import com.Uber.UberApp.model.DriverElasticDocument;
import com.Uber.UberApp.repository.DriverDocumentRepository;
import com.Uber.UberApp.service.IDriverIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DriverIndexServiceImpl implements IDriverIndexService {

    private final DriverDocumentRepository driverDocumentRepository;

    @Override
    public void createDriverIndex(Driver driver) {

        DriverElasticDocument document=DriverElasticDocument.builder()
                .id(String.valueOf(driver.getId()))
                .name(driver.getName())
                .email(driver.getEmail())
                .licenseNumber(driver.getLicenseNumber())
                .build();
        driverDocumentRepository.save(document);

    }
}
