package com.example.demo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "results_links")
public class ResultsLinks {

    public enum LinkType {
        ONLINE, OFFLINE
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true, length = 10)
    private LinkType type;

    @Column(name = "link", nullable = false, length = 500)
    private String link;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    // Пустой конструктор
    public ResultsLinks() {}

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LinkType getType() { return type; }
    public void setType(LinkType type) { this.type = type; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}