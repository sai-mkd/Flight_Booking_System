package com.flightbooking.entity;

import com.flightbooking.enums.PassengerGender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passengers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private PassengerGender gender;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}