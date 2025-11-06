package com.Uber.UberApp.mapper;

import com.Uber.UberApp.dto.DriverRequest;
import com.Uber.UberApp.dto.DriverResponse;
import com.Uber.UberApp.model.Driver;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public static Driver toEntity(DriverRequest request) {
        return Driver.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .licenseNumber(request.getLicenseNumber())
                .vehicleModel(request.getVehicleModel())
                .vehiclePlateNumber(request.getVehiclePlateNumber())
                .isAvailable(request.getIsAvailable())
                .build();
    }
    public  static DriverResponse toResponse(Driver driver) {
        return DriverResponse.builder()
                .id(driver.getId())
                .name(driver.getName())
                .email(driver.getEmail())
                .phoneNumber(driver.getPhoneNumber())
                .licenseNumber(driver.getLicenseNumber())
                .vehicleModel(driver.getVehicleModel())
                .vehiclePlateNumber(driver.getVehiclePlateNumber())
                .isAvailable(driver.getIsAvailable())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .build();
    }

    public  static void updateEntity(Driver driver, DriverRequest request) {
        driver.setName(request.getName());
        driver.setEmail(request.getEmail());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setVehicleModel(request.getVehicleModel());
        driver.setVehiclePlateNumber(request.getVehiclePlateNumber());
        driver.setIsAvailable(request.getIsAvailable());
    }
}
