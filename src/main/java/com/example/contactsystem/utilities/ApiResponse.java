package com.example.contactsystem.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static ResponseEntity<Map<String, Object>> generateResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Map<String, Object>> success(String message) {
        return generateResponse(HttpStatus.OK, message, null);
    }

    public static ResponseEntity<Map<String, Object>> success(String message, Object data) {
        return generateResponse(HttpStatus.OK, message, data);
    }

    public static ResponseEntity<Map<String, Object>> created(String message, Object data) {
        return generateResponse(HttpStatus.CREATED, message, data);
    }

    public static ResponseEntity<Map<String, Object>> error(String message, Object data) {
        return generateResponse(HttpStatus.BAD_REQUEST, message, data);
    }

    public static ResponseEntity<Map<String, Object>> notFound(String message) {
        return generateResponse(HttpStatus.NOT_FOUND, message, null);
    }
}
