package com.petrolal.ahun.ahunbirthdayservice.domain.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
