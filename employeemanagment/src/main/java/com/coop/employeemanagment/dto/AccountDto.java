package com.coop.employeemanagment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDto {
    private long id;
    private String username;
    private String password;

    public AccountDto(String username) {
        this.username = username;
    }

    public AccountDto(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
