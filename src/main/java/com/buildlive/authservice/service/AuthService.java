package com.buildlive.authservice.service;

import com.buildlive.authservice.dto.OptRequest;
import com.buildlive.authservice.dto.OtpDto;
import com.buildlive.authservice.dto.OtpResponse;
import com.buildlive.authservice.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthService {



    String generateToken(String userName,String role);
    ResponseEntity<OtpDto> registerUser(RegisterRequest request);

    void validateToken(String token);

    boolean isUserExists(String username,String email);

    ResponseEntity <OtpResponse>verifyAccount(OptRequest request);
}
