package com.example.demo.controller;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.dto.RegistrationResponse;
import com.example.demo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> register(
            @RequestHeader("Authorization") String initData,
            @RequestBody RegistrationRequest request) {

        try {
            RegistrationResponse response = registrationService.register(initData, request);
            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest()
                    .body(new RegistrationResponse("Invalid initData format"));

        } catch (IllegalArgumentException | org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(409)
                    .body(new RegistrationResponse(e.getMessage()));
        }
    }
}