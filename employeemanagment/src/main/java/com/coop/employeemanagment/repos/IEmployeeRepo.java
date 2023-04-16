package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.dto.EmployeeDto;
import com.coop.employeemanagment.infrastructures.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Long> {
    Employee save(EmployeeDto employeeDto);

}
