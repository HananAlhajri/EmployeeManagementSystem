package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.dto.DepartmentDto;
import com.coop.employeemanagment.infrastructures.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IDepartmentRepo extends JpaRepository<Department, Long> {

    Department save(Department department);

    Department save(DepartmentDto department);
}
