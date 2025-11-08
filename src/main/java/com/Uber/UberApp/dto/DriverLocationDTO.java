package com.Uber.UberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDTO {

    private String  driverId;
    private Double latitude;
    private Double longitude;
}
