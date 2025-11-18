package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"PS.BO.NewYearAdventSettings\"")
public class NewYearAdventSettings {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "couponEmissionId")
    private UUID couponEmissionId;
}