package com.buildlive.authservice.dto;

import com.buildlive.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private boolean isBlocked;
    private boolean isVerified;
    private Role roles;
}
