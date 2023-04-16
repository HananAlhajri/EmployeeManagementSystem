package com.coop.employeemanagment.services;

import com.coop.employeemanagment.dto.DeptEmpDto;
import com.coop.employeemanagment.dto.EmployeeDto;
import com.coop.employeemanagment.infrastructures.constans.Role;
import com.coop.employeemanagment.infrastructures.entity.Department;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.infrastructures.entity.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.coop.employeemanagment.helper.HelperClass.isEmployeeExists;
import static com.coop.employeemanagment.infrastructures.constans.Role.*;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    @Autowired
    private final IEmployeeRepo employeeRepo;


    public Stream<DeptEmpDto> findAllEmployees() {
        return employeeRepo.findAll().stream().map(employee -> new DeptEmpDto(
                employee.getId(), employee.getFirstName(),
                employee.getFatherName(),
                employee.getGrandFatherName(),
                employee.getLastName(), employee.getRole(), employee.getAccount(),
                employee.getDepartment().stream().map(Department::getName)
                        .collect(Collectors.toList())));
    }

    public List<EmployeeDto> getEmployeeById(Long employeeId) {
        if (!isEmployeeExists(employeeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+employeeId+ "does not exist");
        return employeeRepo.findById(employeeId).stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(), employee.getFirstName(),
                        employee.getFatherName(), employee.getGrandFatherName(),
                        employee.getLastName(), employee.getNationalID(),
                        employee.getDateOfBirth(), employee.getRole() ,employee.getAccount()))
                        .toList();
    }

//    public Object addCEO(Employee newCEO) {
//        employeeRepo.save(newCEO);
//        return employeeRepo.findById(newCEO.getId()).stream()
//                .map(employee -> new DeptEmpDto(
//                        employee.getId(), employee.getFirstName(),
//                        employee.getFatherName(),
//                        employee.getGrandFatherName(),
//                        employee.getLastName(), employee.getRole(), employee.getAccount()));
//    }

    public List<EmployeeDto> addEmployee(EmployeeDto newEmployee, Long authorizeId) {
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+authorizeId+ "does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        if (authorizeEmployee.getRole() == CEO) {
            if (newEmployee.getRole() == CEO)
                    throw new IllegalStateException("You are not authorized. CEOs are only authorized to give MANAGER or EMPLOYEE role.");
            else
                newEmployee.setActive(true);
            employeeRepo.save(newEmployee);
        }
        if (authorizeEmployee.getRole() == MANAGER) {
            if (newEmployee.getRole() == USER) {
                newEmployee.setActive(false);
                employeeRepo.save(newEmployee);
            } else
                throw new IllegalStateException("You are not authorized. MANAGERs are only authorized to give EMPLOYEE role.");
        }
        if (authorizeEmployee.getRole() == USER)
             throw new IllegalStateException("You are not authorized. Only CEO's and MANAGERs can add an employee.");

        return employeeRepo.findById(newEmployee.getId()).stream()
                .map(employee -> new EmployeeDto(
                        employee.getId(), employee.getFirstName(),
                        employee.getFatherName(), employee.getGrandFatherName(),
                        employee.getLastName(), employee.getNationalID(),
                        employee.getDateOfBirth(), employee.getRole(),employee.getAccount()))
                        .toList();
    }

    @Transactional
    public Object updateRole(Long employeeId_toUpdate, Role role, Long authorizeId) {
        if (!isEmployeeExists(employeeId_toUpdate, employeeRepo))
            throw new IllegalStateException("Employee with id: "+employeeId_toUpdate+ "does not exist");
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+authorizeId+ "does not exist");


        Employee employee = employeeRepo.findById(employeeId_toUpdate).get();
        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();

        if (authorizeEmployee.getRole() == CEO) {
            employee.setRole(role);
            return employeeRepo.save(employee);
        } else if (authorizeEmployee.getRole() == MANAGER) {
            if (role == USER) {
                employee.setRole(role);
                return employeeRepo.save(employee);
            } else return "You are not authorized. MANAGERs are allowed to give EMPLOYEE role only.";
        } else return "You are not authorized. Only CEOs and MANAGERs are allowed to update employee role.";
    }

    public Boolean deleteEmployeeById(Long employeeId) {
        if (!isEmployeeExists(employeeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+employeeId+ "does not exist");

        employeeRepo.deleteById(employeeId);
        return true;
    }

//    public Boolean isEmployeeExists(Long id, IEmployeeRepo employeeRepo) {
//        if (employeeRepo.findById(id).isEmpty()) {
//            return false;
//        }
//        else return true;
//    }

}
