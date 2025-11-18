package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramAuthServiceTest {

    @Autowired
    private TelegramAuthService telegramAuthService;

    @Test
    void shouldExtractTgIdFromValidInitData() throws JsonProcessingException {
        String initData = "query_id=AAA&user=%7B%22id%22%3A123456789%7D&auth_date=1731930000&hash=123";
        assertEquals(123456789L, telegramAuthService.getTgIdFromInitData(initData));
    }

    @Test
    void shouldThrowWhenNoUser() {
        String initData = "query_id=AAA&auth_date=1731930000&hash=123";
        assertThrows(IllegalArgumentException.class, () -> telegramAuthService.getTgIdFromInitData(initData));
    }
}