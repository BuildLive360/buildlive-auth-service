package com.buildlive.authservice.dto;

import com.buildlive.authservice.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private UserCredential user;
    private String token;
}
