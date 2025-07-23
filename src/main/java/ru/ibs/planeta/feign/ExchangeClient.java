package ru.ibs.planeta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;
import ru.ibs.planeta.model.dto.ProjectsResponseDto;

@FeignClient(name = "external-service", url = "${loader.departments-url}")
public interface ExchangeClient {

    @GetMapping("/departments")
    DepartmentsResponseDto getDepartments();

    @GetMapping("/projects")
    ProjectsResponseDto getProjects();

}
