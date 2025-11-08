package com.Uber.UberApp.service.impl;

import com.Uber.UberApp.dto.DriverLocationDTO;
import com.Uber.UberApp.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;

import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisLocationServiceImpl implements LocationService {

    private static final String DRIVER_GEO_OPS_KEY="driver:geo";
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {
        GeoOperations<String,String> geoOperations=stringRedisTemplate.opsForGeo();
        geoOperations.add(DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(driverId,new Point(latitude,longitude)));

        return  true;
    }

    @Override
    public List<DriverLocationDTO> getNearbyDrivers(Double latitude, Double longitude, Double radius) {
       GeoOperations<String,String> geoOperations=stringRedisTemplate.opsForGeo();

        Distance radiusDistance=new Distance(radius, Metrics.KILOMETERS);

        Circle circle=new Circle(new Point(latitude,longitude),radiusDistance);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results=geoOperations.radius(DRIVER_GEO_OPS_KEY,circle);

        List<DriverLocationDTO> driverLocations=new ArrayList<>();

        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result:results){
            Point point=geoOperations.position(DRIVER_GEO_OPS_KEY,result.getContent().getName()).get(0);

            DriverLocationDTO driverLocationDTO=DriverLocationDTO.builder()
                    .driverId(result.getContent().getName())
                    .longitude(point.getX())
                    .latitude(point.getX())
                    .build();

            driverLocations.add(driverLocationDTO);


        }
        return driverLocations;
    }
}
