package com.coop.employeemanagment.dto;

import com.coop.employeemanagment.infrastructures.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    private Employee manager;
    @JsonIgnore
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
