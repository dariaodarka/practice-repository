package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "\"PS.BO.NewYearAdventProgress\"")  // кавычки важны!
public class NewYearAdventProgress {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "clientId")
    private UUID clientId;

    @Column(name = "dateId")
    private UUID dateId;

    @Column(name = "isPassed")
    private Boolean isPassed;

    @Column(name = "isPassedSuccessed")  // ← ТОЧНО ТАК, без "e" в конце!
    private Boolean isPassedSuccessed;
}