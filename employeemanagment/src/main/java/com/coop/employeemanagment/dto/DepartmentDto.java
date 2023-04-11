package com.coop.employeemanagment.dto;

import com.coop.employeemanagment.infrastructures.entity.Employee;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
    private Employee manager;
    private List<Employee> employees;

    public DepartmentDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public DepartmentDto(Long id, String name, String description, Employee manager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
    }
    public DepartmentDto(Long id, String name, String description, Employee manager, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.employees = employees;

    }
}
