package com.coop.employeemanagment.controllers;

import com.coop.employeemanagment.dto.EmployeeDto;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.models.ResponseModel;
import com.coop.employeemanagment.infrastructures.constans.APIs;
import com.coop.employeemanagment.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(APIs.Employee.baseUrl)
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping(APIs.Employee.addEmployee + "{yourId}")
    public ResponseEntity<ResponseModel> addEmployee(@RequestBody EmployeeDto employee, @Param("yourId") Long authorizeId) {
        return ok(ResponseModel.builder()
                .data(Map.of("result", employeeService.addEmployee(employee, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Employee.getEmployeeById + "{employeeId}")
    public ResponseEntity<ResponseModel> getEmployeeById(@Param("employeeId") Long employeeId) {
        return ok(ResponseModel.builder().data(Map.of("result", employeeService.getEmployeeById(employeeId))).
                message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

//    @PostMapping("/addCEO")
//    public ResponseEntity<ResponseModel> addCEO(@RequestBody Employee employee) {
//        return ok(ResponseModel.builder()
//                .data(Map.of("Added CEO: ", employeeService.addCEO(employee)))
//                .message("Successful request")
//                .status(HttpStatus.OK)
//                .statusCode(HttpStatus.OK.value()).build());
//    }

    @GetMapping(APIs.Employee.getAllEmployee + "/emp")
    public ResponseEntity<ResponseModel> getAllEmployee() {
        return ok(ResponseModel.builder()
                .data(Map.of("result", employeeService.findAllEmployees()))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @PutMapping(APIs.Employee.updateEmployee + "/{employeeId}/{yourId}")
    public ResponseEntity<ResponseModel> updateEmployeeRole(@Param("employeeId") Long employeeId, @Valid @RequestBody Role role, @Param("yourId") Long authorizeId) {
        return ok(ResponseModel.builder()
                .data(Map.of("result", employeeService.updateRole(employeeId, role, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @DeleteMapping(APIs.Employee.deleteEmployee + "/{id}")
    public ResponseEntity<ResponseModel> deleteEmployee(@PathVariable("id") Long employeeId) {
        return ok(
                ResponseModel.builder()
                        .data(Map.of("result", employeeService.deleteEmployeeById(employeeId)))
                        .message("Successful request")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
    }

}
