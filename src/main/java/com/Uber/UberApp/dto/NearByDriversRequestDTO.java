package com.Uber.UberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearByDriversRequestDTO {

    private Double latitude;
    private Double longitude;
    private Double radius;
}
