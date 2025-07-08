package com.example.springboot.ExceptionHandler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}