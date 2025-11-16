package com.example.demo.repository;

import com.example.demo.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResultsRepository extends JpaRepository<Results, UUID> {
    List<Results> findAll();
}