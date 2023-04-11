package com.coop.employeemanagment.controllers;

import com.coop.employeemanagment.infrastructures.constans.APIs;
import com.coop.employeemanagment.infrastructures.entity.Account;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.models.ResponseModel;
import com.coop.employeemanagment.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(APIs.Account.baseUrl)
public class AccountController {

    @Autowired
    private final AccountService accountService;

    @PostMapping(APIs.Account.AddAccount)
    public ResponseEntity<ResponseModel> addAccount(@RequestBody Account account){
        return ok(ResponseModel.builder()
                .data(Map.of("Added new Account: ", accountService.addAccount(account)))
                .message("Successful request")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Account.allAccounts)
    public ResponseEntity<ResponseModel> allAccounts(){
        List<Object> employees = accountService.findAllAccounts();
        return ResponseEntity.ok(ResponseModel.builder()
                .data(Map.of("All Accounts: ", employees))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }
}
