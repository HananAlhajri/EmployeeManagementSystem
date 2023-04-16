package com.coop.employeemanagment.controllers;

import com.coop.employeemanagment.infrastructures.constans.APIs;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.models.ResponseModel;
import com.coop.employeemanagment.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(APIs.Role.baseUrl)
public class RoleController {

    private final RoleService roleService;

    @PostMapping(APIs.Role.addRole)
    public ResponseEntity<ResponseModel> addRole(@RequestBody Role role){
        return ok(ResponseModel.builder()
                .data(Map.of("Added new role: ", roleService.addRole(role)))
                .message("Successful request")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Role.allRoles)
    public ResponseEntity<ResponseModel> allRoles(){
        List<List<Role>> employees = roleService.findAllRoles();
        return ResponseEntity.ok(ResponseModel.builder()
                .data(Map.of("All Roles: ", employees))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }
}
