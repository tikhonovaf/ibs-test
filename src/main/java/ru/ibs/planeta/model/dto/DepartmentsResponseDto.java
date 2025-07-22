package ru.ibs.planeta.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class DepartmentsResponseDto {

    private List<DepartmentDto> departments;
}
