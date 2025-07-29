package ru.ibs.planeta.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.ibs.planeta.model.dto.ProjectsResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeWebClient {

    private final WebClient webClient;

    public Mono<DepartmentsResponseDto> getDepartments() {
        return webClient.get()
                .uri("/departments")
                .retrieve()
                .bodyToMono(DepartmentsResponseDto.class);
    }

    public Mono<ProjectsResponseDto> getProjects() {
        return webClient.get()
                .uri("/projects")
                .retrieve()
                .bodyToMono(ProjectsResponseDto.class);
    }
}
