package ru.ibs.planeta.jpa.repo;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ibs.planeta.jpa.entity.Department;

import java.util.List;
import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d WHERE :ids IS NULL OR d.depId NOT IN :ids")
    List<Department> findAllByIdNotIn(@Param("ids") Set<Long> ids);
}
