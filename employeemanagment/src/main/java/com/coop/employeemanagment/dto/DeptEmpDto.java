package com.coop.employeemanagment.dto;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeptEmpDto {
    private long empId;
    private String empFirstName;
    private String empfatherName;
    private String empGrandFatherName;
    private String empLastName;
    private Role empRole;
    private List<String> departments;
    private String username;

    public DeptEmpDto(long empId, String empFirstName, String empfatherName, String empGrandFatherName, String empLastName, Role empRole, Account empAccount, List<String> departments) {
        this.empId = empId;
        this.empFirstName = empFirstName;
        this.empfatherName = empfatherName;
        this.empGrandFatherName = empGrandFatherName;
        this.empLastName = empLastName;
        this.empRole = empRole;
        this.username = empAccount.getUsername();
        this.departments = departments;
    }
    public DeptEmpDto(long empId, String empFirstName, String empfatherName, String empGrandFatherName, String empLastName, Role empRole, Account empAccount) {
        this.empId = empId;
        this.empFirstName = empFirstName;
        this.empfatherName = empfatherName;
        this.empGrandFatherName = empGrandFatherName;
        this.empLastName = empLastName;
        this.empRole = empRole;
        this.username = empAccount.getUsername();
    }

    public DeptEmpDto(String empFirstName, String empMiddleName, String empLastName, Role empRole, List<String> departments) {
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.empfatherName = empMiddleName;
        this.empRole = empRole;
        this.departments = departments;
    }

    public DeptEmpDto(String empFirstName, String empMiddleName, String empLastName, Role empRole) {
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.empfatherName = empMiddleName;
        this.empRole = empRole;
    }


}