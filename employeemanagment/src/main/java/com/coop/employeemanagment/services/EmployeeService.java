package com.coop.employeemanagment.services;

import com.coop.employeemanagment.dto.DeptEmpDto;
import com.coop.employeemanagment.dto.EmployeeDto;
import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.infrastructures.entity.Department;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.infrastructures.entity.Employee;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.coop.employeemanagment.helper.HelperClass.isEmployeeExists;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {
    private final IEmployeeRepo employeeRepo;


    public List<DeptEmpDto> findAllEmployees() {
        return employeeRepo.findAll().stream().map(ed -> new DeptEmpDto(
                ed.getId(), ed.getFirstName(),
                ed.getFatherName(),
                ed.getGrandFatherName(),
                ed.getLastName(), ed.getRole(), ed.getAccount(),
                ed.getDepartment().stream().map(Department::getName)
                        .collect(Collectors.toList()))).toList();
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepo.findById(employeeId).orElseThrow(
                () ->  new IllegalStateException("Employee with id: " + employeeId + "does not exist"));
    }

    public Employee addEmployee(EmployeeDto empDTO, Long authorizeId) {
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: " + authorizeId + "does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        Employee employee = new Employee();
        employee.setFirstName(empDTO.getFirstName());
        employee.setFatherName(empDTO.getFatherName());
        employee.setGrandFatherName(empDTO.getGrandFatherName());
        employee.setLastName(empDTO.getLastName());
        employee.setNationalID(empDTO.getNationalID());
        employee.setDateOfBirth(empDTO.getDateOfBirth());
        employee.setJobTitle(empDTO.getJobTitle());
        employee.setAccount(empDTO.getAccount());
        employee.setRole(empDTO.getRole());

        if (authorizeEmployee.getRole().getId() == 1) {
            if (empDTO.getRole().getId() == 1)
                    throw new IllegalStateException("You are not authorized. CEOs are only authorized to give MANAGER or EMPLOYEE role.");
            else{
                employee.setActive(true);
                 return employeeRepo.save(employee);}
        }
        if (authorizeEmployee.getRole().getId() == 2) {
            if (empDTO.getRole().getId() == 3) {
                employee.setActive(false);
                return employeeRepo.save(employee);
            } else
                throw new IllegalStateException("You are not authorized. MANAGERs are only authorized to give EMPLOYEE role.");
        }
        if (authorizeEmployee.getRole().getId() == 3)
             throw new IllegalStateException("You are not authorized. Only CEO's and MANAGERs can add an employee.");

        return employeeRepo.save(employee);
    }

    @Transactional
    public List<EmployeeDto> updateRole(Long employeeId_toUpdate, Role role, Long authorizeId) {
        if (!isEmployeeExists(employeeId_toUpdate, employeeRepo))
            throw new IllegalStateException("Employee with id: " + employeeId_toUpdate + "does not exist");
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: " + authorizeId + "does not exist");


        Employee employee = employeeRepo.findById(employeeId_toUpdate).get();
        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();

        if (authorizeEmployee.getRole().getId() == 1) {
            employee.setRole(role);
            employeeRepo.save(employee);
            return employeeRepo.findById(employee.getId()).map(e -> new EmployeeDto(
                            e.getId(), e.getFirstName(),
                            e.getFatherName(), e.getGrandFatherName(),
                            e.getLastName(), e.getNationalID(),
                            e.getDateOfBirth(), e.getJobTitle(),
                            e.getRole(), e.getAccount().getUsername())).stream().toList();
        } else if (authorizeEmployee.getRole().getId() == 2) {
            if (role.getId() == 3) {
                employee.setRole(role);
                employeeRepo.save(employee);
                return employeeRepo.findById(employee.getId()).map(e -> new EmployeeDto(
                        e.getId(), e.getFirstName(),
                        e.getFatherName(), e.getGrandFatherName(),
                        e.getLastName(), e.getNationalID(),
                        e.getDateOfBirth(), e.getJobTitle(),
                        e.getRole(), e.getAccount().getUsername())).stream().toList();
            } else throw new IllegalStateException("You are not authorized. MANAGERs are allowed to give EMPLOYEE role only.");
        } else throw new IllegalStateException("You are not authorized. Only CEOs and MANAGERs are allowed to update employee role.");
    }

    public Long deleteEmployeeById(Long employeeId) {
        if (!isEmployeeExists(employeeId, employeeRepo))
            throw new IllegalStateException("Employee with id: " + employeeId + "does not exist");

        employeeRepo.deleteById(employeeId);
        return employeeId;
    }

}
