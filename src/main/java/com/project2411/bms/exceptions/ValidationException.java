package com.project2411.bms.exceptions;
// Exception class for validation errors
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}