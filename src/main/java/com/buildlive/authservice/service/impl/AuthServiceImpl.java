package com.buildlive.authservice.service.impl;

import com.buildlive.authservice.dto.RegisterRequest;
import com.buildlive.authservice.dto.RegisterResponse;
import com.buildlive.authservice.entity.Role;
import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
import com.buildlive.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;





    @Override
    public String generateToken(String userName,String role){
        return jwtService.generateToken(userName,role);
    }

    @Override
    public ResponseEntity<RegisterResponse> registerUser(RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        try {
            if (isUserExists(request.getName(), request.getEmail())) {
                response.setStatus("Failed");
                response.setMessage("Username or email already exists, Try using another one");
                return ResponseEntity.badRequest().body(response);
            }
            UserCredential user = saveUser(request);
            response.setStatus("success");
            response.setMessage("User Registered");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            RegisterResponse exceptionResponse = new RegisterResponse();
            exceptionResponse.setStatus("error");
            exceptionResponse.setMessage("Failed " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
        }
    }


    @Override
    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    @Override
    public boolean isUserExists(String username, String email) {
        Optional<UserCredential> userByUsername = userCredentialRepository.findByName(username);
        Optional<UserCredential> userByEmail = userCredentialRepository.findByEmail(email);

        if(userByUsername.isPresent() || userByEmail.isPresent()){
            return true;
        }
        return false;
    }


    private UserCredential saveUser(RegisterRequest request) {
        UserCredential userCredential = new UserCredential();
        userCredential.setName(request.getName());
        userCredential.setEmail(request.getEmail());
        userCredential.setPhone(request.getPhone());
        userCredential.setPassword(passwordEncoder.encode(request.getPassword()));
        userCredential.setBlocked(false);
        userCredential.setVerified(false);
        userCredential.setRoles(Role.USER);
        return userCredentialRepository.save(userCredential);
    }
}
