package ru.ibs.planeta.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.planeta.jpa.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
