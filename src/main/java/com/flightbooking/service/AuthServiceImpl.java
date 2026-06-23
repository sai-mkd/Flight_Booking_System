package com.flightbooking.service;

import com.flightbooking.dto.LoginRequest;
import com.flightbooking.dto.RegisterRequest;
import com.flightbooking.entity.User;
import com.flightbooking.enums.Role;
import com.flightbooking.repository.UserRepository;
import com.flightbooking.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);
    }
    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid credentials");
        }

        return jwtService.generateToken(
                user.getEmail());
    }
}
