package com.buildlive.authservice.controller;

import com.buildlive.authservice.dto.AuthRequest;
import com.buildlive.authservice.dto.AuthResponse;
import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/test")
        public String test(){
            return "hello";
        }

           @PutMapping("/users/edit-user")
            public ResponseEntity <UserCredential> editUser(
                                                    @RequestBody  UserCredential userCredential
                                                    ){
               System.out.println("id"+userCredential.getId());

                return ResponseEntity.ok(authService.editUser(userCredential));
            }

    @GetMapping("/users/get-company")
    @CircuitBreaker(name = "user-service",fallbackMethod = "serviceFallBackMethod")
    public ResponseEntity<AuthResponse> getCompany(@RequestBody AuthRequest authRequest){
        UserCredential user = userCredentialRepository.findByName(authRequest.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuthResponse response =  authService.getCompanyById(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<AuthResponse> serviceFallBackMethod(Exception e){
        return (ResponseEntity<AuthResponse>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }


}
