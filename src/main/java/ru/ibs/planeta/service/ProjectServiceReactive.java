package ru.ibs.planeta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.ibs.planeta.feign.ExchangeClient;
import ru.ibs.planeta.model.dto.ProjectsResponseDto;
import ru.ibs.planeta.webclient.ExchangeWebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceReactive {

    private final ExchangeWebClient exchangeWebClient;

    public Mono<ProjectsResponseDto> getProjects() {
        return exchangeWebClient.getProjects()
                .onErrorResume(e -> Mono.just(new ProjectsResponseDto())); // Fallback
    }}
