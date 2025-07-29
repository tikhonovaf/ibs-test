package ru.ibs.planeta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ibs.planeta.feign.ExchangeClient;
import ru.ibs.planeta.jpa.entity.Department;
import ru.ibs.planeta.jpa.repo.DepartmentRepository;
import ru.ibs.planeta.model.dto.DepartmentDto;
import ru.ibs.planeta.model.dto.DepartmentsResponseDto;
import ru.ibs.planeta.model.dto.ProjectDto;
import ru.ibs.planeta.model.mapper.DepartmentMapper;
import ru.ibs.planeta.webclient.ExchangeWebClient;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceReactive {

    private final ExchangeWebClient exchangeWebClient;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final ProjectServiceReactive projectServiceReactive;


    //    @Async
    @Scheduled(cron = "${loader.cron}")
    public CompletableFuture<Integer> loadDepartments() {
//    public void loadDepartments() {
        log.info("Start loading departments");
        System.out.println("Scheduled task running in thread: " + Thread.currentThread().getName());


//        List <ProjectDto>  projects = projectService.loadProjects();

        Map<Long, Long> depPrjCount = projectServiceReactive.loadProjects().getProjects()
                .stream()
                .filter(ProjectDto::getActive)
                .collect(Collectors.groupingBy(ProjectDto::getDepId, Collectors.counting()));

        final DepartmentsResponseDto departments = exchangeClient.getDepartments();

        // 1. Получаем все DepId из загруженного списка подразделений
        Set<Long> newDepIds = departments.getDepartments().stream()
                .map(DepartmentDto::getDepId)
                .collect(Collectors.toSet());

        // 2. Находим записи в БД, которых нет в загруженном списке
        List<Department> toDelete = departmentRepository.findAllByIdNotIn(newDepIds);

        // 3. Удаляем их
        departmentRepository.deleteAll(toDelete);

        // 4. Сохраняем загруженные данные
        departmentRepository.saveAll(
                departments.getDepartments()
                        .stream()
//                        .map(departmentMapper::toEntity)
                        .map(dto -> departmentMapper.toEntity(dto, depPrjCount.get(dto.getDepId())))
                        .toList()
        );

        log.info("End loading departments: {}", departments.getDepartments().size());
        return CompletableFuture.completedFuture(departments.getDepartments().size());

    }
}
