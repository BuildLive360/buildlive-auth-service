package com.buildlive.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Map;

public interface JwtService {

    void validateToken(final String token);
    String createToken(Map<String,Object> claims, String userName,String role);
    String generateToken(String userName,String role);
}
