package com.coop.employeemanagment.controllers;

import com.coop.employeemanagment.infrastructures.constans.APIs;
import com.coop.employeemanagment.models.ResponseModel;
import com.coop.employeemanagment.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(APIs.Account.baseUrl)
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(APIs.Account.login+"{username}/{password}")
    public ResponseEntity<ResponseModel> login(@PathVariable String username, @PathVariable String password){
        return ok(ResponseModel.builder()
                .data(Map.of("", loginService.login(username, password)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Account.logout)
    public ResponseEntity<ResponseModel> logout(){
        return ok(ResponseModel.builder()
                .message("You are logged out successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

}
