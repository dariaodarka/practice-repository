package com.example.demo.repository;

import com.example.demo.model.NewYearAdventProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewYearAdventProgressRepository extends JpaRepository<NewYearAdventProgress, UUID> {
    List<NewYearAdventProgress> findByClientId(UUID clientId);}