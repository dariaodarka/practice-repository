package com.example.demo.controller;

import com.example.demo.dto.ResultItemDto;
import com.example.demo.dto.ResultsResponseDto;
import com.example.demo.model.Results;
import com.example.demo.model.Users;
import com.example.demo.repository.ResultsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.util.InitDataParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultsController {

    private final UsersRepository usersRepository;
    private final ResultsRepository resultsRepository;

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
}