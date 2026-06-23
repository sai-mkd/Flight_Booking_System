package com.flightbooking.entity;

import com.flightbooking.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}