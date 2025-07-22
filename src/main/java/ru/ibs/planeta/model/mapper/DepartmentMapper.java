package ru.ibs.planeta.model.mapper;

import org.mapstruct.Mapper;
import ru.ibs.planeta.jpa.entity.Department;
import ru.ibs.planeta.model.dto.DepartmentDto;

@Mapper
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDto);
}
