package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.repos.IAccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final IAccountRepo accountRepo;

    public Account addAccount(Account account){return accountRepo.save(account);}

    public List<List<Account>> findAllAccounts() {return Collections.singletonList(accountRepo.findAll());}
}
