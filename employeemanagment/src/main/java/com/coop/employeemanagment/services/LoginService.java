package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.repos.IAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoginService {

    @Autowired
    private IAccountRepo accountRepo;

    @Autowired
    private IEmployeeRepo employeeRepo;


    public Object login(String username, String password) {

        Account result = accountRepo.findOneByUsernameAndPassword(username, password);

        if(result == null)
            return "You are not authorized";
        else if (result.getEmployee().isActive() == false)
            return "You have not been activated";
        else
            return "Welcome you are logged in";
    }

}
