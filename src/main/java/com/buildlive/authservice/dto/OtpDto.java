package com.buildlive.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {

    private UUID id;
    private String email;
    private String name;
    private boolean isVerified;
    private String otp;
    private String status;
    private String message;

}
