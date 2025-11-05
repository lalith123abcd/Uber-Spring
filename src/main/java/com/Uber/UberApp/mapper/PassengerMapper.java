package com.Uber.UberApp.mapper;

import com.Uber.UberApp.dto.PassengerRequest;
import com.Uber.UberApp.dto.PassengerResponse;
import com.Uber.UberApp.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {


    public static Passenger toEntity(PassengerRequest request){
        return Passenger.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static PassengerResponse toResponse(Passenger passenger){
        return PassengerResponse.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
                .updatedAt(passenger.getUpdatedAt())
                .build();
    }

    public  static void updateEntity(Passenger passenger,PassengerRequest request){

        passenger.setName(request.getName());
        passenger.setEmail(request.getEmail());
        passenger.setPhoneNumber(request.getPhoneNumber());

    }


}
