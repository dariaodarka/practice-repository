package com.example.demo.repository;

import com.example.demo.model.ResultsLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ResultsLinksRepository extends JpaRepository<ResultsLinks, UUID> {
    Optional<ResultsLinks> findByType(ResultsLinks.LinkType type);
}