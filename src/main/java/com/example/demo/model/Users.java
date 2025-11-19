package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "clientId", nullable = false)
    private UUID clientId;

    @Column(name = "telegramId", unique = true, nullable = false)
    private String telegramId;

    @Column(name = "promoCode")
    private String promoCode;

    // ИСПРАВЛЕНО: Boolean вместо boolean + правильное имя колонки
    @Column(name = "isAdmin", nullable = false)
    private Boolean isAdmin = false;   // ← теперь Lombok сгенерит getIsAdmin()
}