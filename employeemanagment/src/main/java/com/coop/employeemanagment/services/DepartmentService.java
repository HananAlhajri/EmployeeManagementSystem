package com.coop.employeemanagment.services;

import com.coop.employeemanagment.dto.DepartmentDto;
import com.coop.employeemanagment.infrastructures.entity.Department;
import com.coop.employeemanagment.infrastructures.entity.Employee;
import com.coop.employeemanagment.repos.IDepartmentRepo;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.coop.employeemanagment.helper.HelperClass.*;
import static com.coop.employeemanagment.infrastructures.constans.Role.*;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    @Autowired
    private final IDepartmentRepo departmentRepo;
    @Autowired
    private final IEmployeeRepo employeeRepo;

    public Stream<DepartmentDto> findAllDepartments(Long authorizeId){
        if(!isEmployeeExists(authorizeId,employeeRepo))
           throw new IllegalStateException("Employee with id "+authorizeId+ " does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
            if (authorizeEmployee.getRole() == CEO) {
                return departmentRepo.findAll().stream()
                        .map(department -> new DepartmentDto(
                                department.getId(),
                                department.getName(),
                                department.getDescription(),
                                department.getDepartmentManager(),
                                department.getEmployees().stream()
                                        .collect(Collectors.toList())));
            }
        throw new IllegalStateException("You are not authorized. Only CEOs can view the departments information ");
    }

    public Stream<DepartmentDto> managerDepartments(Long authorizeId) {
        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        if(isEmployeeExists(authorizeId,employeeRepo)) {
            if (authorizeEmployee.getRole() == MANAGER) {
                return authorizeEmployee.getDepartment()
                        .stream().map(department -> new DepartmentDto(department.getId(),
                                department.getName(),
                                department.getDescription(),
                                department.getDepartmentManager(),
                                department.getEmployees().stream()
                                        .collect(Collectors.toList())));
            }
        }
        throw new IllegalStateException("Employee with id "+authorizeId+" does not exist");
    }
    public Stream<? extends Object> findDepartmentById(Long departmentId, Long authorizeId){
        if(!isEmployeeExists(authorizeId,employeeRepo))
            throw new IllegalStateException("Employee with id "+authorizeId+ " does not exist");

        if(!isDepartmentExists(departmentId, departmentRepo))
            throw new IllegalStateException("Department with id "+departmentId+ " does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        if(authorizeEmployee.getRole() == CEO || authorizeEmployee.getRole() == MANAGER ) {
            return departmentRepo.findById(departmentId).stream()
                    .map(department -> new DepartmentDto(department.getId(),
                            department.getName(),
                            department.getDescription(),
                            department.getDepartmentManager()));
        }
        return null;
    }

    public Department addDepartment(DepartmentDto department, Long authorizeId) {
        if(!isEmployeeExists(authorizeId,employeeRepo))
            throw new IllegalStateException("Employee with id "+authorizeId+ " does not exist");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
            if (authorizeEmployee.getRole() !=  CEO)
                throw new IllegalStateException("You are not authorized to create a department.");

            else return departmentRepo.save(department);
    }

    @Transactional
    public Department addManagerInDepartment (Long deptId, Long managerId, Long authorizeId) {
        if(!isDepartmentExists(deptId, departmentRepo))
            throw new IllegalStateException("Department with id "+deptId+ " does not exist");

        if(isTheyExist(managerId,authorizeId, employeeRepo)) {
            Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
            Employee manager = employeeRepo.findById(managerId).get();
            Department department = departmentRepo.findById(deptId).get();
            if (authorizeEmployee.getRole() != CEO)
                throw new IllegalStateException("You are not authorized. Only CEOs can assign managers to departments.");

            department.setDepartmentManager(manager);
            return departmentRepo.save(department);
        }
        throw new IllegalStateException("Manager Id or Authorized Id does NOT exists");
    }

    @Transactional
    public Department addEmployeeInDepartment (Long deptId, Long empId, Long authorizeId){
        if(!isDepartmentExists(deptId, departmentRepo))
            throw new IllegalStateException("Department with id "+deptId+ " does not exist");

        if(!isTheyExist(empId, authorizeId, employeeRepo))
            throw new IllegalStateException("Manager Id or Authorized Id does NOT exists");

        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        Department department = departmentRepo.findById(deptId).get();
        Employee employee = employeeRepo.findById(empId).get();

        if(isTheyExist(empId,authorizeId,employeeRepo)) {
            if (authorizeEmployee.getRole() == CEO)
                department.getEmployees().add(employee);
            else if (authorizeEmployee.getRole() == MANAGER) {
                if (department.getDepartmentManager().getId() == authorizeId)
                    department.getEmployees().add(employee);
            }
            else throw new IllegalStateException("You are not authorized. Only CEO's and MANAGERs can add employee to department.");
        }
        return departmentRepo.save(department);
    }

    @Transactional
    public Department updateDepartment (Long departmentId, DepartmentDto departmentDetails, Long authorizeId){
        if(!isDepartmentExists(departmentId,departmentRepo))
            throw new IllegalStateException("Department with id "+departmentId+ " does not exist");

        if(!isEmployeeExists(authorizeId,employeeRepo))
            throw new IllegalStateException("Employee with id "+authorizeId+ " does not exist");

        Department department = departmentRepo.findById(departmentId).get();
        Employee authorizeEmployee = employeeRepo.findById(authorizeId).get();
        if(authorizeEmployee.getRole() != CEO)
            throw new IllegalStateException("You are not authorized to update a department.");

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        if(department.getDepartmentManager() != null) {
            Long managerId = department.getDepartmentManager().getId();
            //Employee manager = employeeRepo.findById(managerId).get();
            addManagerInDepartment(departmentId, managerId , authorizeId );
        }
        return departmentRepo.save(department);
    }

    public Object deleteDepartment(Long departmentId, Long authorizeId) {
        if(!isDepartmentExists(departmentId,departmentRepo))
            throw new IllegalStateException("Department with id "+departmentId+ " does not exist");

        if(!isEmployeeExists(authorizeId,employeeRepo))
            throw new IllegalStateException("Employee with id "+authorizeId+ " does not exist");

        Department deleteDepartment = departmentRepo.getReferenceById(departmentId);
        Employee employee = employeeRepo.findById(authorizeId).get();

        if(employee.getRole() != CEO)
           return "You are not authorized to delete a department.";
        if (deleteDepartment.getDepartmentManager() != null || !deleteDepartment.getEmployees().isEmpty())
            return "Can not delete department with employees or a manager in it.";

        departmentRepo.deleteById(departmentId);
        return departmentId;
    }
}
