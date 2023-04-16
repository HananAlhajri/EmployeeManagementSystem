package com.coop.employeemanagment.dto;

import com.coop.employeemanagment.infrastructures.constans.Role;
import com.coop.employeemanagment.infrastructures.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {
    private long id;
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String lastName;
    private long nationalID;
    private LocalDate dateOfBirth;
    private Account account;
    private Role role;
    private boolean isActive;

    public EmployeeDto(long id, String firstName, String fatherName, String grandFatherName, String lastName, long nationalID, LocalDate dateOfBirth, Role role, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.account = account;
    }
}
