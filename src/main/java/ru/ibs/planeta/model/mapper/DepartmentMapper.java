package ru.ibs.planeta.model.mapper;

//import org.mapstruct.Mapper;
//import org.springframework.stereotype.Service;
//import ru.ibs.planeta.jpa.entity.Department;
//import ru.ibs.planeta.model.dto.DepartmentDto;
//
//@Mapper
//public interface DepartmentMapper {
//
//    Department toEntity(DepartmentDto departmentDto);
//}

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.ibs.planeta.jpa.entity.Department;
import ru.ibs.planeta.model.dto.DepartmentDto;

@Mapper(componentModel = "spring") // <-- This is the key
public interface DepartmentMapper {
    @Mapping(target = "prjCount", source = "prjCount")
    Department toEntity(DepartmentDto departmentDto, Long prjCount);
}