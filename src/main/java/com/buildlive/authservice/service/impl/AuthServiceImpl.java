package com.buildlive.authservice.service.impl;

import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
import com.buildlive.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public String saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "user added";
    }

    @Override
    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    @Override
    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
