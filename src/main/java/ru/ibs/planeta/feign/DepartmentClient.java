package ru.ibs.planeta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;

@FeignClient(name = "external-service", url = "${loader.departments-url}")
public interface DepartmentClient {

    @GetMapping("/departments")
    DepartmentsResponseDto getDepartments();
}
