package ru.ibs.planeta.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectsResponseDto {

    private List<ProjectDto> projects;
}
