package ru.ibs.planeta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ibs.planeta.feign.ExchangeClient;
import ru.ibs.planeta.model.dto.ProjectsResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ExchangeClient exchangeClient;

    public ProjectsResponseDto  loadProjects() {
        log.info("Start loading Projects");
        return exchangeClient.getProjects();

    }
}
