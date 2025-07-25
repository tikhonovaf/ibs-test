package ru.ibs.planeta.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentDto {

    @JsonProperty("dep_id")
    private Long depId;
    private String code;
    private String name;
}