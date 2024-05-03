package com.buildlive.authservice.controller;

import com.buildlive.authservice.dto.OptRequest;
import com.buildlive.authservice.dto.OtpDto;
import com.buildlive.authservice.dto.OtpResponse;
import com.buildlive.authservice.dto.RegisterRequest;
import com.buildlive.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AdminController {

    @Autowired
    AuthService authService;


    @PostMapping("/admin-register")
    public ResponseEntity<OtpDto> addNewUser(@RequestBody RegisterRequest request){
        return authService.registerAdmin(request);
    }

    @PostMapping("/admin-verifyotp")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OptRequest request){
        System.out.println("ooo");
        return authService.verifyAdminAccount(request);
    }

}
