package com.buildlive.authservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID id;
    private String companyName;
    private String cityName;
    private String address;
    private String phoneNumber;
    private String GSTNumber;
    private String PANNumber;
    private UUID owner;
    private boolean companyIsNotPresent;
}
