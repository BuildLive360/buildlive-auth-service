package com.buildlive.authservice.service;

import com.buildlive.authservice.dto.RegisterRequest;
import com.buildlive.authservice.dto.RegisterResponse;
import com.buildlive.authservice.entity.UserCredential;
import org.springframework.http.ResponseEntity;

public interface AuthService {



    String generateToken(String userName,String role);
    ResponseEntity<RegisterResponse> registerUser(RegisterRequest request);

    void validateToken(String token);

    boolean isUserExists(String username,String email);
}
