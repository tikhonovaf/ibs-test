package ru.ibs.planeta.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "departments")
@Data
public class Department {

    @Id
    @Column(name = "dep_id", nullable = false)
    private Long depId;
    private String code;
    private String name;
}
