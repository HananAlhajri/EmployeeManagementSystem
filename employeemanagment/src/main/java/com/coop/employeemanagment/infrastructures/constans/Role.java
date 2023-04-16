package com.coop.employeemanagment.infrastructures.constans;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

public enum Role {
    CEO(1),
    MANAGER(2),
    USER(3);
//    private Long role;

    Role(int role) {
    }
}
