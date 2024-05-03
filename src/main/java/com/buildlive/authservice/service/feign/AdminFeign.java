package com.buildlive.authservice.service.feign;

import com.buildlive.authservice.dto.OtpDto;
import com.buildlive.authservice.dto.VerifyDto;
import com.buildlive.authservice.entity.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ADMIN-SERVICE")
public interface AdminFeign {

    @PostMapping("/api/v1/admin/save-admin")
    ResponseEntity<UserCredential> createAdmin(@RequestBody OtpDto otpDto);

    @PostMapping("/api/v1/admin/verify-admin")
    ResponseEntity<UserCredential> verifyAdmin(@RequestBody VerifyDto verifyDto);
}
