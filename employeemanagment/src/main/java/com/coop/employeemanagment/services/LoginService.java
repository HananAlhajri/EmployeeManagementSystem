package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.repos.IAccountRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class LoginService {

    private IAccountRepo accountRepo;

    public Object login(String username, String password) {

        Account result = accountRepo.findOneByUsernameAndPassword(username, password);

        if(result == null)
            return "You are not authorized";
        else if (!result.getEmployee().isActive())
            return "You have not been activated";
        else
            return "Welcome you are logged in";
    }

}
