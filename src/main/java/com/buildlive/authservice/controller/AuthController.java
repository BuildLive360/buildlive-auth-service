package com.buildlive.authservice.controller;

import com.buildlive.authservice.dto.*;
import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.exception.InvalidLoginException;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserCredentialRepository userCredentialRepository;

//    @PostMapping("/register")
//    public String addNewUser(@RequestBody UserCredential user){
//        return authService.saveUser(user);
//    }

    @PostMapping("/register")
    public ResponseEntity<OtpDto> addNewUser(@RequestBody RegisterRequest request){
        return authService.registerUser(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OptRequest request){
        System.out.println("ooo");
          return authService.verifyAccount(request);
    }



    @PostMapping("/user-login")
    public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest.getName());


        UserCredential user = userCredentialRepository.findByName(authRequest.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(authRequest.getPassword());

        if (user == null) {
            throw new InvalidLoginException("Invalid user");
        }
        if (user.isBlocked()) {
            throw new InvalidLoginException("Blocked user");
        }

        String token = null;
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            token = authService.generateToken(authRequest.getName(), user.getRoles().toString());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUser(user);
            authResponse.setToken(token);

            return ResponseEntity.ok(authResponse);
        } else {

            throw new RuntimeException(" invalid access");
        }
    }
//
//    }
//    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
//
//    }





//    @PostMapping("/token")
//    public String getToken(@RequestBody AuthRequest authRequest){
//
//
//
//       Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(),authRequest.getPassword()));
//       if(authenticate.isAuthenticated()){
//           return authService.generateToken(authRequest.getName());
//       }
//       else {
//           throw new RuntimeException(" invalid access");
//       }
//
//    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){

        authService.validateToken(token);
        return "Token is valid";
    }


}
