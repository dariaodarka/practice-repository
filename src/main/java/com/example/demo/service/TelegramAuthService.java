package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class TelegramAuthService {

    private final ObjectMapper mapper = new ObjectMapper();

    public Long getTgIdFromInitData(String initData) throws JsonProcessingException {
        String[] parts = initData.split("&");
        for (String part : parts) {
            if (part.startsWith("user=")) {
                String userJson = part.substring(5);
                userJson = java.net.URLDecoder.decode(userJson, java.nio.charset.StandardCharsets.UTF_8);
                JsonNode userNode = mapper.readTree(userJson);
                return userNode.get("id").asLong();
            }
        }
        throw new IllegalArgumentException("No user in initData");
    }
}