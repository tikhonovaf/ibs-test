package ru.ibs.planeta.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProjectDto {

    @JsonProperty("prj_id")
    private Long prjId;
    @JsonProperty("dep_id")
    private Long depId;
    private String code;
    private String name;
    private Boolean active;

}