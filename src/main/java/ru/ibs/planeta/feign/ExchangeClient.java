package ru.ibs.planeta.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;
import ru.ibs.planeta.model.dto.ProjectsResponseDto;

@FeignClient(name = "external-service", url = "${loader.departments-url}")
public interface ExchangeClient {

//    @CircuitBreaker(name = "external-service")
    @Retry(name = "external-service")
    @GetMapping("/departments")
    DepartmentsResponseDto getDepartments();

//    @CircuitBreaker(name = "external-service")
    @Retry(name = "external-service")
    @GetMapping("/projects")
    ProjectsResponseDto getProjects();

}
