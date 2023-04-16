package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.infrastructures.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface IDepartmentRepo extends JpaRepository<Department, Long> {

    Department save(Department department);

    Set<Department> findAllByDepartmentManagerId(Long authorizeId);
}
