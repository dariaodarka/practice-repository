package com.example.demo.service;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.dto.RegistrationResponse;
import com.example.demo.model.NewYearAdventSettings;
import com.example.demo.model.NewYearAdventProgress;
import com.example.demo.model.Users;
import com.example.demo.repository.NewYearAdventSettingsRepository;
import com.example.demo.repository.NewYearAdventProgressRepository;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final TelegramAuthService telegramAuthService;
    private final UsersRepository usersRepository;
    private final NewYearAdventSettingsRepository settingsRepository;
    private final NewYearAdventProgressRepository progressRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Transactional
    public RegistrationResponse register(String initDataHeader, RegistrationRequest request)
            throws JsonProcessingException {

        // 1. Парсим tgId из initData
        Long tgId = telegramAuthService.getTgIdFromInitData(initDataHeader);

        // 2. Проверяем, не зарегистрирован ли уже (по telegramId как String!)
        if (usersRepository.findByTelegramId(String.valueOf(tgId)).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already registered");
        }

        // 3. Генерируем промокод
        String promoCode = generateCode();

        // 4. Создаём пользователя в ps.bo.users
        Users user = new Users();
        user.setClientId(UUID.randomUUID());
        user.setTelegramId(String.valueOf(tgId));   // ← String!
        user.setPromoCode(promoCode);               // ← promoCode!
        user.setAdmin(false);
        usersRepository.save(user);

        // 5. Создаём записи в адвент-прогрессе (если даты уже залиты)
        List<NewYearAdventSettings> allDates = settingsRepository.findAll();
        for (NewYearAdventSettings setting : allDates) {
            NewYearAdventProgress progress = new NewYearAdventProgress();
            progress.setClientId(user.getClientId());
            progress.setDateId(setting.getId());
            progress.setIsPassed(false);
            progress.setIsPassedSuccessed(false);
            progressRepository.save(progress);
        }

        // 6. Возвращаем промокод клиенту
        return new RegistrationResponse(promoCode);
    }

    private String generateCode() {
        StringBuilder code = new StringBuilder(11);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                code.append('-');
            } else {
                code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
        }
        return code.toString(); // например: A1B-2C3-D4E
    }
}