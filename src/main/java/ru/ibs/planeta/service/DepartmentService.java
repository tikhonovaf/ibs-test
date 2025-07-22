package ru.ibs.planeta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ibs.planeta.feign.DepartmentClient;
import ru.ibs.planeta.jpa.repo.DepartmentRepository;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;
import ru.ibs.planeta.model.mapper.DepartmentMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentClient departmentClient;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Async
    @Scheduled(cron = "${loader.cron}")
    public void loadDepartments() {
        log.info("Start loading departments");

        final DepartmentsResponseDto departments = departmentClient.getDepartments();

        departmentRepository.saveAll(
            departments.getDepartments()
                .stream()
                .map(departmentMapper::toEntity)
                .toList()
        );

        log.info("End loading departments: {}", departments.getDepartments().size());
    }
}
