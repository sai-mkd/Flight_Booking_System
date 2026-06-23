package com.flightbooking.config;

import com.flightbooking.security.
        JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.
        HttpSecurity;
import org.springframework.security.config.http.
        SessionCreationPolicy;
import org.springframework.security.web.
        SecurityFilterChain;
import org.springframework.security.web.authentication.
        UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // ✅ THYMELEAF / WEB PAGES (ADD THIS)
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/search-flights",
                                "/book/**",
                                "/my-bookings",
                                "/css/**",
                                "/admin-dashboard",
                                "/js/**"
                        ).permitAll()

                        // ✅ AUTH APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        // 🔐 ADMIN APIs
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // 👤 CUSTOMER APIs
                        .requestMatchers("/api/customer/**")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                        // ✈️ FLIGHT APIs
                        .requestMatchers("/api/flights/**")
                        .hasAnyRole("CUSTOMER", "ADMIN")

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // everything else must be authenticated
                        .anyRequest().authenticated()
                )

                // 🔥 IMPORTANT: JWT filter stays
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}