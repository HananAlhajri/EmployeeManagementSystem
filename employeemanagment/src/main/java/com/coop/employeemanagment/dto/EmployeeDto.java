package com.coop.employeemanagment.dto;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private long id;
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String lastName;
    private long nationalID;
    private LocalDate dateOfBirth;
    private Account account;
    @JsonIgnore
    private String username;
    private String jobTitle;
    private Role role;

    public EmployeeDto(long id, String firstName, String fatherName, String grandFatherName, String lastName, long nationalID, LocalDate dateOfBirth, String jobTitle, Role role, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.dateOfBirth = dateOfBirth;
        this.jobTitle = jobTitle;
        this.role = role;
        this.account = account;
    }
    public EmployeeDto(long id, String firstName, String fatherName, String grandFatherName, String lastName, long nationalID, LocalDate dateOfBirth, String jobTitle, Role role, String username) {
        this.id = id;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.dateOfBirth = dateOfBirth;
        this.jobTitle = jobTitle;
        this.role = role;
        this.username = username;
    }
}
