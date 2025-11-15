package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    private UUID id;
    private UUID clientId;
    private String telegramId;
    private String promoCode;
    private boolean isAdmin;
}