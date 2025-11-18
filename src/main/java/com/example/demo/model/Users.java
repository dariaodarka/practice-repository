package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")  // ← ЭТО ГЛАВНОЕ!
public class Users {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "clientId", nullable = false)
    private UUID clientId;

    @Column(name = "telegramId", unique = true, nullable = false)
    private String telegramId;        // ← String!

    @Column(name = "promoCode")
    private String promoCode;         // ← promoCode, а не registrationCode

    @Column(name = "isAdmin", nullable = false)
    private boolean admin = false;  // ← по умолчанию false
}