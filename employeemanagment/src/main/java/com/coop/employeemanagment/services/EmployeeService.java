package com.coop.employeemanagment.services;

import com.coop.employeemanagment.dto.DeptEmpDto;
import com.coop.employeemanagment.dto.EmployeeDto;
import com.coop.employeemanagment.infrastructures.entity.Role;
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
                        employee.getDateOfBirth(), employee.getJobTitle(), employee.getRole()
                        ,employee.getAccount().getUsername()))
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

    public List<EmployeeDto> addEmployee(EmployeeDto empDTO, Long authorizeId) {
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+authorizeId+ "does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        Employee employee = new Employee();
        employee.setFirstName(empDTO.getFirstName());
        employee.setFatherName(empDTO.getFatherName());
        employee.setGrandFatherName(empDTO.getGrandFatherName());
        employee.setLastName(empDTO.getLastName());
        employee.setNationalID(empDTO.getNationalID());
        employee.setDateOfBirth(empDTO.getDateOfBirth());
        employee.setJobTitle(empDTO.getJobTitle());
        String username = (empDTO.getFirstName()).substring(0,1) + empDTO.getLastName().substring(0,2);
        employee.setAccount(empDTO.getAccount());
        employee.setRole(empDTO.getRole());

        if (authorizeEmployee.getRole().getId() == 1) {
            if (empDTO.getRole().getId() == 1)
                    throw new IllegalStateException("You are not authorized. CEOs are only authorized to give MANAGER or EMPLOYEE role.");
            else{
                employee.setActive(true);
                 employeeRepo.save(employee);}
        }
        if (authorizeEmployee.getRole().getId() == 2) {
            if (empDTO.getRole().getId() == 3) {
                employee.setActive(false);
                employeeRepo.save(employee);
            } else
                throw new IllegalStateException("You are not authorized. MANAGERs are only authorized to give EMPLOYEE role.");
        }
        if (authorizeEmployee.getRole().getId() == 3)
             throw new IllegalStateException("You are not authorized. Only CEO's and MANAGERs can add an employee.");

        return employeeRepo.findById(empDTO.getId()).stream()
                .map(e -> new EmployeeDto(
                        e.getId(), e.getFirstName(),
                        e.getFatherName(), e.getGrandFatherName(),
                        e.getLastName(), e.getNationalID(),
                        e.getDateOfBirth(), e.getJobTitle(),
                        e.getRole(), e.getAccount()))
                        .toList();
    }

    @Transactional
    public List<EmployeeDto> updateRole(Long employeeId_toUpdate, Role role, Long authorizeId) {
        if (!isEmployeeExists(employeeId_toUpdate, employeeRepo))
            throw new IllegalStateException("Employee with id: "+employeeId_toUpdate+ "does not exist");
        if (!isEmployeeExists(authorizeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+authorizeId+ "does not exist");


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

    public Boolean deleteEmployeeById(Long employeeId) {
        if (!isEmployeeExists(employeeId, employeeRepo))
            throw new IllegalStateException("Employee with id: "+employeeId+ "does not exist");

        employeeRepo.deleteById(employeeId);
        return true;
    }

}
