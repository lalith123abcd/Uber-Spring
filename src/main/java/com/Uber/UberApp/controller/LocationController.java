package com.Uber.UberApp.controller;

import com.Uber.UberApp.dto.DriverLocationDTO;
import com.Uber.UberApp.dto.NearByDriversRequestDTO;
import com.Uber.UberApp.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/driverLocation")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody DriverLocationDTO driverLocation) {
        Boolean saved = locationService.saveDriverLocation(driverLocation.getDriverId(), driverLocation.getLatitude(), driverLocation.getLongitude());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/nearbyDrivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearbyDrivers(@RequestBody NearByDriversRequestDTO request) {
        List<DriverLocationDTO> nearbyDrivers = locationService.getNearbyDrivers(request.getLatitude(), request.getLongitude(), request.getRadius());
        return ResponseEntity.ok(nearbyDrivers);
    }
}
