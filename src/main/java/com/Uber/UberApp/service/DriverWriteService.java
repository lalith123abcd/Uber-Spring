package com.Uber.UberApp.service;

import com.Uber.UberApp.dto.DriverRequest;
import com.Uber.UberApp.dto.DriverResponse;

public interface DriverWriteService {
    DriverResponse create(DriverRequest request);
    DriverResponse update(Long id, DriverRequest request);
    void deleteById(Long id);
}
