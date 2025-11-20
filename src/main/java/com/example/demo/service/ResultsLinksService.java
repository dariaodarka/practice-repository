package com.example.demo.service;

import com.example.demo.dto.ResultsLinksRequest;
import com.example.demo.model.ResultsLinks;
import com.example.demo.repository.ResultsLinksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultsLinksService {

    private final ResultsLinksRepository repository;

    public ResultsLinksService(ResultsLinksRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void updateLinks(ResultsLinksRequest request, String adminTgId) {
        if (request.online() != null && !request.online().isBlank()) {
            saveOrUpdate(ResultsLinks.LinkType.ONLINE, request.online().trim(), adminTgId);
        }
        if (request.offline() != null && !request.offline().isBlank()) {
            saveOrUpdate(ResultsLinks.LinkType.OFFLINE, request.offline().trim(), adminTgId);
        }
    }

    private void saveOrUpdate(ResultsLinks.LinkType type, String link, String adminTgId) {
        ResultsLinks entity = repository.findByType(type)
                .orElse(new ResultsLinks());
        entity.setType(type);
        entity.setLink(link);
        entity.setCreatedBy(adminTgId);
        repository.save(entity);
    }
}