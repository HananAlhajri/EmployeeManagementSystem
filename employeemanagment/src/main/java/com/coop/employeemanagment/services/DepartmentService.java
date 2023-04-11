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
import com.coop.employeemanagment.helper.HelperClass;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    @Autowired
    private final IDepartmentRepo departmentRepo;
    @Autowired
    private final IEmployeeRepo employeeRepo;

    private String result; // to get the result from (helper class) functions.

    public Stream<? extends Object> findAllDepartments(Long authorizeId){
        result = HelperClass.isEmployeeExists(authorizeId);
        if(!result.equals("Yes"))
            return result.lines();

            Employee authorizeEmployee = employeeRepo.getOne(authorizeId);
            if (authorizeEmployee.getRole().getId() == 1) {
                return departmentRepo.findAll().stream()
                        .map(department -> new DepartmentDto(
                                department.getId(),
                                department.getName(),
                                department.getDescription(),
                                department.getDepartmentManager(),
                                department.getEmployees().stream()
                                        .collect(Collectors.toList())));
            }
            else
                return "You are not authorized. Only CEOs can view the departments information ".lines();
    }

    public Stream<? extends Object> managerDepartments(Long authorizeId) {
        Optional<Employee> authorizeEmployee = employeeRepo.findById(authorizeId);
        result = HelperClass.isEmployeeExists(authorizeId);
        if (result.equals("Yes")) {
            if (authorizeEmployee.get().getRole().getId() == 2) {
                return authorizeEmployee.get().getDepartment()
                        .stream().map(department -> new DepartmentDto(department.getId(),
                                department.getName(),
                                department.getDescription(),
                                department.getDepartmentManager(),
                                department.getEmployees().stream()
                                        .collect(Collectors.toList())));
            }
        }
        return result.lines();
    }
    public Stream<? extends Object> findDepartmentById(Long departmentId, Long authorizeId){
        result = HelperClass.isEmployeeExists(authorizeId);
        if(!result.equals("Yes"))
            return result.lines();
        result = HelperClass.isDepartmentExists(departmentId);
        if(!result.equals("Yes"))
            return result.lines();

        Optional<Employee> authorizeEmployee = employeeRepo.findById(authorizeId);
        if(authorizeEmployee.get().getRole().getId() == 1 || authorizeEmployee.get().getRole().getId() == 2 ) {
            return departmentRepo.findById(departmentId).stream()
                    .map(department -> new DepartmentDto(department.getId(),
                            department.getName(),
                            department.getDescription(),
                            department.getDepartmentManager()));
        }
        return null;
    }

    public Object addDepartment(Department department, Long authorizeId) {
        result = HelperClass.isEmployeeExists(authorizeId);
        if(!result.equals("Yes"))
            return result.lines();

        Optional<Employee> authorizeEmployee = employeeRepo.findById(authorizeId);
            if (authorizeEmployee.get().getRole().getId() != 1)
                return "You are not authorized to create a department.";

        return departmentRepo.save(department);
    }

    @Transactional
    public Object addManagerInDepartment (Long deptId, Long managerId, Long authorizeId) {
        result = HelperClass.isDepartmentExists(deptId);
        if (!result.equals("Yes"))
            return result.lines();
        
        if(HelperClass.isTheyExist(managerId,authorizeId)) {
            Employee authorizeEmployee = employeeRepo.getOne(authorizeId);
            Employee manager = employeeRepo.getOne(managerId);
            Department department = departmentRepo.getOne(deptId);
            if (authorizeEmployee.getRole().getId() != 1)
                return "You are not authorized. Only CEOs can assign managers to departments.";

            department.setDepartmentManager(manager);
          //addEmployeeInDepartment(deptId, Manager.getId(), authorizeId);
            return departmentRepo.save(department);
        }
        return "Id does NOT exists";
    }

    @Transactional
    public Object addEmployeeInDepartment (Long deptId, Long empId, Long authorizeId){
        result = HelperClass.isDepartmentExists(deptId);
        if(!result.equals("Yes"))
            return result.lines();
        if(!HelperClass.isTheyExist(empId, authorizeId))
            return "Employee id does Not exist";

        Optional<Employee> authorizeEmployee = employeeRepo.findById(authorizeId);
        Department department = departmentRepo.getReferenceById(deptId);
        Employee employee = employeeRepo.getReferenceById(empId);

        if(HelperClass.isTheyExist(empId,authorizeId)) {
            if (authorizeEmployee.get().getRole().getId() == 1)
                department.getEmployees().add(employee);
            else if (authorizeEmployee.get().getRole().getId() == 2) {
                if (department.getDepartmentManager().getId() == authorizeId)
                    department.getEmployees().add(employee);
            }
            else return "You are not authorized. Only CEO's and MANAGERs can add employee to department.";
        }
        return departmentRepo.save(department);
    }

    @Transactional
    public Object updateDepartment (Long departmentId, Department departmentDetails, Long authorizeId){
        result = HelperClass.isDepartmentExists(departmentId);
        if(!result.equals("Yes"))
            return result.lines();
        result = HelperClass.isEmployeeExists(authorizeId);
        if(!result.equals("Yes"))
            return result.lines();

        Department department = departmentRepo.getOne(departmentId);
        Employee authorizeEmployee = employeeRepo.getOne(authorizeId);
        if(authorizeEmployee.getRole().getId() != 1)
            return "You are not authorized to update a department.";

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());
        if(department.getDepartmentManager() != null) {
            //get Employee info
            Long managerId = department.getDepartmentManager().getId();
            Employee manager = employeeRepo.getReferenceById(managerId);
            addManagerInDepartment(departmentId, managerId , authorizeId );
        }
        return departmentRepo.save(department);
    }

    public Object deleteDepartment(Long departmentId, Long authorizeId) {
        result = HelperClass.isDepartmentExists(departmentId);
        if(!result.equals("Yes"))
            return result.lines();
        result = HelperClass.isEmployeeExists(authorizeId);
        if(!result.equals("Yes"))
            return result.lines();

        Department deleteDepartment = departmentRepo.getReferenceById(departmentId);
        Optional<Employee> employee = employeeRepo.findById(authorizeId);

        if(employee.get().getRole().getId() != 1)
           return "You are not authorized to delete a department.";
        if (deleteDepartment.getDepartmentManager() != null || !deleteDepartment.getEmployees().isEmpty())
            return "Can not delete department with employees or a manager in it.";

        departmentRepo.deleteById(departmentId);
        return departmentId;
    }
}
