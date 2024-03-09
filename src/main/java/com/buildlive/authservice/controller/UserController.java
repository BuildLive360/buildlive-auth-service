package com.buildlive.authservice.controller;

import com.buildlive.authservice.dto.AuthRequest;
import com.buildlive.authservice.dto.AuthResponse;
import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
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

           @PutMapping("/users/edit/{id}")
            public ResponseEntity <UserCredential> editUser(@PathVariable UUID id,
                                                    @RequestBody  UserCredential userCredential
                                                    ){
               System.out.println("id"+id);

                return ResponseEntity.ok(authService.editUser(id,userCredential));
            }

    @GetMapping("/users/get-company")
    public ResponseEntity<AuthResponse> getCompany(@RequestBody AuthRequest authRequest){
        UserCredential user = userCredentialRepository.findByName(authRequest.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuthResponse response =  authService.getCompanyById(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
