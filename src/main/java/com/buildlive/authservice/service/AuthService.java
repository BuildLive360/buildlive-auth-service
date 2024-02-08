package com.buildlive.authservice.service;

import com.buildlive.authservice.entity.UserCredential;

public interface AuthService {


    String saveUser(UserCredential userCredential);
    String generateToken(String userName);

    void validateToken(String token);
}
