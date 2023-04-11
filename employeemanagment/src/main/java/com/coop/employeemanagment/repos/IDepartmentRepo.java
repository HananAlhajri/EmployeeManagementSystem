package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.infrastructures.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDepartmentRepo extends JpaRepository<Department, Long> {

    Object save(Optional<Department> department);
}
