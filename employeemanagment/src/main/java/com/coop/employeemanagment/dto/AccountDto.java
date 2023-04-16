package com.coop.employeemanagment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {
    private String username;
    private String password;
    private String jobTitle;


    public AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
