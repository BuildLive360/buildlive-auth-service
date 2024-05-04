package com.buildlive.authservice.service.feign;

import com.buildlive.authservice.dto.CompanyDto;
import com.buildlive.authservice.dto.OtpDto;
import com.buildlive.authservice.dto.VerifyDto;
import com.buildlive.authservice.entity.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "USER-SERVICE",url = "http://13.127.132.74:8050")
public interface UserFeign {

        @PostMapping("/api/v1/user/save-user")
        ResponseEntity<UserCredential> createUser(@RequestBody OtpDto otpDto);

        @PostMapping("/api/v1/user/verify-user")
        ResponseEntity<UserCredential> verifyUser(@RequestBody VerifyDto verifyDto);


    @GetMapping("api/v1/company/{id}")
    CompanyDto getCompany(@PathVariable UUID id);
}
