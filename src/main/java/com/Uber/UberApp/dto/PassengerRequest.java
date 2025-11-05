package com.Uber.UberApp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {

    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "name is required")
    private String email;

    @NotBlank(message = "name is required")
    private String phoneNumber;
}
