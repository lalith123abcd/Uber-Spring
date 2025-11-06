package com.Uber.UberApp.controller;

import com.Uber.UberApp.dto.PassengerRequest;
import com.Uber.UberApp.dto.PassengerResponse;
import com.Uber.UberApp.model.PassengerElasticDocument;
import com.Uber.UberApp.service.PassengerService;
import com.Uber.UberApp.service.PassengerWriteService;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@Valid @RequestBody PassengerRequest passengerRequest){
      try{

          PassengerResponse passenger=passengerService.createPassenger(passengerRequest);
          return ResponseEntity.status(HttpStatus.CREATED).body(passenger);

      } catch (Exception e) {
         return ResponseEntity.badRequest().build();
      }



    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(@PathVariable Long id,
                                                             @Valid @RequestBody PassengerRequest passengerRequest){


        try {
            PassengerResponse passenger=passengerService.updatePassenger(id,passengerRequest);
            return ResponseEntity.ok(passenger);

        } catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getAllPassengers(){
        List<PassengerResponse> passengers=passengerService.findAll();
        return ResponseEntity.ok(passengers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id){
        return passengerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PassengerResponse> getPassengerByEmail(@PathVariable String email){

        return passengerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id){

        try{
            passengerService.deleteById(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PassengerElasticDocument>> getByElasticSearch(@RequestParam String query){

        List<PassengerElasticDocument> documents= passengerService.findByElasticSearch(query);
        return ResponseEntity.ok(documents);
    }



}
