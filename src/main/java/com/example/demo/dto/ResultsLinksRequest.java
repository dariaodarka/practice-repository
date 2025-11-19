package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultsLinksRequest(
        @JsonProperty("online") String online,
        @JsonProperty("offline") String offline
) {}