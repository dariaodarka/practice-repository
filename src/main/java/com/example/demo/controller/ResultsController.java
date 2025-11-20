package com.example.demo.controller;

import com.example.demo.dto.ResultItemDto;
import com.example.demo.dto.ResultsResponseDto;
import com.example.demo.dto.ResultsLinksRequest;
import com.example.demo.model.Results;
import com.example.demo.model.Users;
import com.example.demo.repository.ResultsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.ResultsLinksService;
import com.example.demo.util.InitDataParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping // можно оставить пустым, если уже есть @RequestMapping на классе выше
public class ResultsController {

    private final UsersRepository usersRepository;
    private final ResultsRepository resultsRepository;
    private final ResultsLinksService resultsLinksService;

    // Существующий GET /results — остаётся без изменений
    @GetMapping("/results")
    public ResultsResponseDto getResults(@RequestHeader("initData") String initData) {
        String tgId = InitDataParser.extractTelegramId(initData);

        Users user = usersRepository.findByTelegramId(tgId)
                .orElseThrow(() -> new RuntimeException("00920002"));

        String promoCode = user.getPromoCode();

        List<Results> resultsList = resultsRepository.findAll();

        List<ResultItemDto> items = resultsList.stream().map(r -> {
            List<String> winners = usersRepository.findByClientId(r.getClientId())
                    .stream()
                    .map(Users::getPromoCode)
                    .toList();
            return new ResultItemDto(r.getId(), r.getName(), winners);
        }).toList();

        return new ResultsResponseDto(promoCode, items);
    }

    // НОВЫЙ ЭНДПОИНТ — только для админов
    @PostMapping("/results/links")
    public ResponseEntity<String> updateResultsLinks(
            @RequestHeader(value = "initData", required = false) String initData,
            @RequestHeader(value = "X-Debug-TgId", required = false) String debugTgId,
            @RequestBody ResultsLinksRequest request
    ) {
        String tgId;

        if (debugTgId != null && !debugTgId.isBlank()) {
            tgId = debugTgId;
            System.out.println("DEBUG MODE: используем tgId = " + tgId);
        } else if (initData != null && !initData.isBlank()) {
            tgId = InitDataParser.extractTelegramId(initData);
        } else {
            return ResponseEntity.badRequest().body("initData or X-Debug-TgId required");
        }

        Users user = usersRepository.findByTelegramId(tgId)
                .orElseThrow(() -> new RuntimeException("00920002"));

        if (!Boolean.TRUE.equals(user.getIsAdmin())) {
            throw new RuntimeException("00920007");
        }

        resultsLinksService.updateLinks(request, tgId);
        return ResponseEntity.ok("Links updated successfully");
    }
}