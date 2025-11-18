package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "results")
public class Results {
    @Id
    private UUID id;
    private String name;
    private UUID clientId;
}