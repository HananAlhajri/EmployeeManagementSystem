package com.coop.employeemanagment.helper;

import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.repos.IDepartmentRepo;

public class HelperClass {

    public HelperClass() {}

    public static Boolean isEmployeeExists(Long id, IEmployeeRepo employeeRepo) {
        return employeeRepo.findById(id).isEmpty();
    }

    public static Boolean isDepartmentExists(Long id, IDepartmentRepo departmentRepo) {
        return departmentRepo.findById(id).isEmpty();
    }
    public static Boolean isTheyExist(Long authorizeId, Long empId, IEmployeeRepo employeeRepo) {
        return employeeRepo.findById(authorizeId).isPresent() &&
                employeeRepo.findById(empId).isPresent();
    }
}
