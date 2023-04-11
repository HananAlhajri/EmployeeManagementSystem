package com.coop.employeemanagment.services;

import com.coop.employeemanagment.dto.DeptEmpDto;
import com.coop.employeemanagment.infrastructures.entity.Department;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.repos.IDepartmentRepo;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.infrastructures.entity.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    @Autowired
    private final IEmployeeRepo employeeRepo;

    private String result;

    public Stream<? extends Object> findAllEmployees() {
        return employeeRepo.findAll().stream().map(employee -> new DeptEmpDto(
                employee.getId(), employee.getFirstName(),
                employee.getFatherName(),
                employee.getGrandFatherName(),
                employee.getLastName(), employee.getRole(), employee.getAccount(),
                employee.getDepartment().stream().map(Department::getName)
                        .collect(Collectors.toList())));
    }

    public Object addCEO(Employee newEmployee) {
        Employee newCEO = employeeRepo.save(newEmployee);
        return employeeRepo.findById(newEmployee.getId()).stream()
                .map(employee -> new DeptEmpDto(
                        employee.getId(), employee.getFirstName(),
                        employee.getFatherName(),
                        employee.getGrandFatherName(),
                        employee.getLastName(), employee.getRole(), employee.getAccount()));
    }

    public Object addEmployee(Employee newEmployee, Long authorizeId) {
        result = isEmployeeExists(authorizeId);
        if (!result.equals("Yes"))
            return result.lines();

            Employee authorizeEmployee = employeeRepo.getOne(authorizeId);
            if(authorizeEmployee.getRole().getId() == 1) {
                if (newEmployee.getRole().getId() == 1)
                    return "You are not authorized. CEOs are only authorized to give MANAGER or EMPLOYEE role.";
                else
                    newEmployee.setActive(true);
                    employeeRepo.save(newEmployee);
            }
            if(authorizeEmployee.getRole().getId() == 2) {
                 if(newEmployee.getRole().getId() == 3) {
                     newEmployee.setActive(false);
                     employeeRepo.save(newEmployee);
                 }
                else return "You are not authorized. MANAGERs are only authorized to give EMPLOYEE role.";
            }
        if (authorizeEmployee.getRole().getId() == 3)
                return "You are not authorized. Only CEO's and MANAGERs can add an employee.";

        return employeeRepo.findById(newEmployee.getId()).stream()
                .map(employee -> new DeptEmpDto(
                        employee.getId(), employee.getFirstName(),
                        employee.getFatherName(),
                        employee.getGrandFatherName(),
                        employee.getLastName(), employee.getRole(), employee.getAccount()));
    }

    @Transactional
    public Object updateRole(Long employeeId_toUpdate, Role role, Long authorizeId) {
        result = isEmployeeExists(employeeId_toUpdate);
        if (!result.equals("Yes"))
            return result.lines();
        result = isEmployeeExists(authorizeId);
        if (!result.equals("Yes"))
            return result.lines();

        Employee employee = employeeRepo.getOne(employeeId_toUpdate);
        Employee authorizeEmployee = employeeRepo.getOne(authorizeId);

        if (authorizeEmployee.getRole().getId() == 1) {
            employee.setRole(role);
            return employeeRepo.save(employee);
        }
        else if (authorizeEmployee.getRole().getId() == 2) {
            if (role.getId() == 3) {
                employee.setRole(role);
                return employeeRepo.save(employee);
            }
            else return "You are not authorized. MANAGERs are allowed to give EMPLOYEE role only.";
        }
         else return "You are not authorized. Only CEOs and MANAGERs are allowed to update employee role.";
    }

    public Object deleteEmployeeById(Long emp_id) {
        result = isEmployeeExists(emp_id);
        if (!result.equals("Yes"))
            return result.lines();

        employeeRepo.deleteById(emp_id);
        return emp_id;
    }

    public String isEmployeeExists(Long id) {
        if (employeeRepo.findById(id).isEmpty()) {
            return "Employee with " + id + " does not exist";
        } else return "Yes";
    }

}
