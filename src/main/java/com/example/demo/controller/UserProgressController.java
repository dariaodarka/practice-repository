package com.example.demo.controller;

import com.example.demo.dto.ProgressItemDto;
import com.example.demo.dto.ProgressResponseDto;
import com.example.demo.model.NewYearAdventSettings;
import com.example.demo.model.NewYearAdventProgress;
import com.example.demo.model.Users;
import com.example.demo.repository.NewYearAdventProgressRepository;
import com.example.demo.repository.NewYearAdventSettingsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.TelegramAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserProgressController {

    private final TelegramAuthService telegramAuthService;
    private final UsersRepository usersRepository;
    private final NewYearAdventSettingsRepository settingsRepository;
    private final NewYearAdventProgressRepository progressRepository;

    @GetMapping("/users/progress")
    public ProgressResponseDto getProgress(@RequestHeader("Authorization") String initData) {

        // ← ЭТО САМОЕ ВАЖНОЕ: используем ТОТ ЖЕ парсер, что и при регистрации
        Long tgId;
        try {
            tgId = telegramAuthService.getTgIdFromInitData(initData);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid initData format");
        }

        Users user = usersRepository.findByTelegramId(String.valueOf(tgId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not registered"));

        UUID clientId = user.getClientId();

        // Все даты адвента
        List<NewYearAdventSettings> allSettings = settingsRepository.findAll();
        if (allSettings.isEmpty()) {
            // Если адвент ещё не настроен — возвращаем пустой прогресс
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new ProgressResponseDto(now, List.of());
        }

        // Прогресс пользователя по dateId
        Map<UUID, NewYearAdventProgress> userProgressMap = progressRepository.findByClientId(clientId)
                .stream()
                .collect(Collectors.toMap(NewYearAdventProgress::getDateId, p -> p));

        LocalDate today = LocalDate.now();

        List<ProgressItemDto> progress = allSettings.stream()
                .map(setting -> {
                    UUID dateId = setting.getId();
                    LocalDate date = setting.getDate();

                    NewYearAdventProgress prog = userProgressMap.get(dateId);

                    // В PDF примере isAvailable = true даже для будущих дат → делаем так же
                    boolean isAvailable = true;
                    // Если потом скажут "только прошедшие даты" — раскомментируй:
                    // boolean isAvailable = !date.isAfter(today);

                    return new ProgressItemDto(
                            dateId,
                            date.toString(), // "2025-12-15"
                            isAvailable,
                            prog != null ? prog.getIsPassed() : null,
                            prog != null ? prog.getIsPassedSuccessed() : null,
                            setting.getCouponEmissionId()
                    );
                })
                .sorted(Comparator.comparing(item -> LocalDate.parse(item.date())))
                .toList();

        String currentDatetime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return new ProgressResponseDto(currentDatetime, progress);
    }
}