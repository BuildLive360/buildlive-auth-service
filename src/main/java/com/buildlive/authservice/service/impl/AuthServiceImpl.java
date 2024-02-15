package com.buildlive.authservice.service.impl;

import com.buildlive.authservice.dto.OptRequest;
import com.buildlive.authservice.dto.OtpDto;
import com.buildlive.authservice.dto.OtpResponse;
import com.buildlive.authservice.dto.RegisterRequest;
import com.buildlive.authservice.entity.Role;
import com.buildlive.authservice.entity.UserCredential;
import com.buildlive.authservice.repository.UserCredentialRepository;
import com.buildlive.authservice.service.AuthService;
import com.buildlive.authservice.service.JwtService;
import com.buildlive.authservice.util.EmailUtil;
import com.buildlive.authservice.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailUtil emailUtil;
    private final OtpUtil otpUtil;






    @Override
    public String generateToken(String userName,String role){
        return jwtService.generateToken(userName,role);
    }

    @Override
    public ResponseEntity<OtpDto> registerUser(RegisterRequest request) {
        System.out.println("coming");
        OtpDto response = new OtpDto();

            if (isUserExists(request.getName(), request.getEmail())) {
                response.setStatus("Failed");
                response.setMessage("Username or email already exists, Try using another one");
                System.out.println("coming2");
                return ResponseEntity.badRequest().body(response);
            }
            String otp = otpUtil.generateOtp();
            System.out.println("3");
            try {

                emailUtil.sendOtpToEmail(request.getEmail(),otp);
                System.out.println("4");
            }
            catch (MessagingException e){
                throw new RuntimeException("Failed,Please send otp again");
            }
            UserCredential user = new UserCredential()
                                 .builder()
                                 .name(request.getName())
                                 .email(request.getEmail())
                                 .phone(request.getPhone())
                                 .password(passwordEncoder.encode(request.getPassword()))
                                 .isBlocked(false)
                                 .isVerified(false)
                                 .otp(otp)
                                 .expiryTime(LocalDateTime.now().plusMinutes(2))
                                 .roles(Role.USER)
                                 .build();
            System.out.println("5");

            userCredentialRepository.save(user);
            System.out.println("6");

            OtpDto otpDto = new OtpDto()
                    .builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .isVerified(false)
                    .status("pending")
                    .message("Verify Otp")
                    .build();

            System.out.println(otpDto.getOtp());

            return ResponseEntity.ok(otpDto);

    }


    @Override
    public void validateToken(String token){
        jwtService.validateToken(token);
    }

    @Override
    public boolean isUserExists(String username, String email) {
        Optional<UserCredential> userByUsername = userCredentialRepository.findByName(username);
        Optional<UserCredential> userByEmail = userCredentialRepository.findByEmail(email);

        if(userByUsername.isPresent() || userByEmail.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<OtpResponse> verifyAccount(OptRequest request) {
        UUID id = request.getUserId();
        OtpResponse otpResponse = new OtpResponse();
        String name = request.getOtpValue();
        System.out.println(name);
        Optional<UserCredential> optionalUser = userCredentialRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserCredential user = optionalUser.get();

            // Compare the user ID from the request and the retrieved user
            if (user.getId().equals(id)) {
                // Verify the OTP
                if (user.getOtp().equals(request.getOtpValue())) {
                    user.setVerified(true);
                    userCredentialRepository.save(user);

                    otpResponse.setStatus("Verified");
                    otpResponse.setMessage("User OTP verified");
                    return ResponseEntity.ok(otpResponse);
                } else {
                    otpResponse.setStatus("Failed");
                    otpResponse.setMessage("Invalid OTP");
                    return ResponseEntity.badRequest().body(otpResponse);
                }
            } else {
                otpResponse.setStatus("Failed");
                otpResponse.setMessage("User ID mismatch");
                return ResponseEntity.badRequest().body(otpResponse);
            }
        } else {
            otpResponse.setStatus("Failed");
            otpResponse.setMessage("User not found");
            return ResponseEntity.badRequest().body(otpResponse);
        }

    }


//    private UserCredential saveUser(RegisterRequest request) {
//        UserCredential userCredential = new UserCredential();
//        userCredential.setName(request.getName());
//        userCredential.setEmail(request.getEmail());
//        userCredential.setPhone(request.getPhone());
//        userCredential.setPassword(passwordEncoder.encode(request.getPassword()));
//        userCredential.setBlocked(false);
//        userCredential.setVerified(false);
//        userCredential.setRoles(Role.USER);
//
//        return userCredentialRepository.save(userCredential);
//    }
}
