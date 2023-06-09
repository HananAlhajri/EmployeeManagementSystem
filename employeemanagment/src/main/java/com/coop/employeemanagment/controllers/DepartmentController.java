package com.coop.employeemanagment.controllers;

import com.coop.employeemanagment.dto.DepartmentDto;
import com.coop.employeemanagment.infrastructures.constans.APIs;
import com.coop.employeemanagment.models.ResponseModel;
import com.coop.employeemanagment.services.DepartmentService;
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
@RequestMapping(APIs.Department.baseUrl)
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping(APIs.Department.getAllDepartments + "{yourId}")
    public ResponseEntity<ResponseModel> getAllDepartments(@Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.findAllDepartments(authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Department.getManagerDepartments + "{yourId}")
    public ResponseEntity<ResponseModel> getManagerDepartments(@Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.managerDepartments(authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @PostMapping(APIs.Department.addDepartment + "{yourId}")
    public ResponseEntity<ResponseModel>addDepartment(@RequestBody DepartmentDto department, @Param("yourId") Long employeeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.addDepartment(department, employeeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @PutMapping(APIs.Department.updateDepartment + "/addEmployeeInDepartment" + "/{departmentId}/{employeeId}/{yourId}")
    public ResponseEntity<ResponseModel> addEmployeeInDepartment(@Param("departmentId") Long deptId, @Param("employeeId") Long empId, @Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.addEmployeeInDepartment(deptId, empId, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @PutMapping(APIs.Department.updateDepartment + "/assignManagerToDepartment" + "/{departmentId}/{managerId}/{yourId}")
    public ResponseEntity<ResponseModel> assignManagerToDepartment(@Param("departmentId") Long deptId, @Param("managerId") Long managerId, @Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.addManagerInDepartment(deptId, managerId, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(APIs.Department.getSpecificDepartment + "/{departmentId}/{yourId}")
    public ResponseEntity<ResponseModel> getDepartmentById(@Param("departmentId") Long departmentId, @Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.findDepartmentById(departmentId, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
       }

    @PutMapping (APIs.Department.updateDepartment + "/{departmentId}/{yourId}")
    public ResponseEntity<ResponseModel> updateDepartment(@PathVariable("departmentId") Long id, @RequestBody DepartmentDto departmentDetails, @Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.updateDepartment(id, departmentDetails, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

    @DeleteMapping (APIs.Department.deleteDepartment + "/{departmentId}" + "/{yourId}")
    public ResponseEntity<ResponseModel> deleteDepartment(@Param("DepartmentID") Long departmentId, @Param("yourId") Long authorizeId){
        return ok(ResponseModel.builder()
                .data(Map.of("result", departmentService.deleteDepartment(departmentId, authorizeId)))
                .message("Successful request")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value()).build());
    }

}
