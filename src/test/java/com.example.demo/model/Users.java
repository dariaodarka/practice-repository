package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "tgId", unique = true, nullable = false)
    private Long tgId;  // Telegram ID — например, 123456789

    @Column(name = "clientId", nullable = false)
    private UUID clientId;

    @Column(name = "registrationCode", unique = true, nullable = false, length = 11)
    private String registrationCode;  // F65-6LW-6W7

    @Column(name = "registeredAt", nullable = false)
    private LocalDateTime registeredAt = LocalDateTime.now();
}