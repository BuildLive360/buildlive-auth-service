package com.buildlive.authservice.service;

import com.buildlive.authservice.dto.*;
import com.buildlive.authservice.entity.UserCredential;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthService {



    String generateToken(String userName,String role);
    ResponseEntity<OtpDto> registerUser(RegisterRequest request);

    void validateToken(String token);

    boolean isUserExists(String username,String email);

    ResponseEntity <OtpResponse>verifyAccount(OptRequest request);

    AuthResponse getCompanyById(UUID uuid);

    UserCredential editUser(UserCredential userCredential);


}
