package com.Uber.UberApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentException(IllegalArgumentException e){
        Map<String,String> error=new HashMap<>();
        error.put("message",e.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.badRequest().body(error);
    }
}
