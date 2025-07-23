package ru.ibs.planeta.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "departments")
@Data
public class Department {

    @Id
    @Column(name = "dep_id", nullable = false)
    private Long depId;
    @Column(name = "prj_count")
    private Long prjCount;
    private String code;
    private String name;
}
