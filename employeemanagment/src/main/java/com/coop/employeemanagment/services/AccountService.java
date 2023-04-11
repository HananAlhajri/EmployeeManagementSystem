package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.repos.IAccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    @Autowired
    private final IAccountRepo accountRepo;

    public Account addAccount(Account account){return accountRepo.save(account);}

    public List<Object> findAllAccounts() {return Collections.singletonList(accountRepo.findAll());}
}
