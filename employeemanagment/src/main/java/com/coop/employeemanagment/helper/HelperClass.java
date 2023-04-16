package com.coop.employeemanagment.helper;

import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.repos.IDepartmentRepo;

public class HelperClass {

    public HelperClass() {}

    public static Boolean isEmployeeExists(Long id, IEmployeeRepo employeeRepo) {
        if (employeeRepo.findById(id).isEmpty()) {
            return false;
        }
        else return true;
    }

    public static Boolean isDepartmentExists(Long id, IDepartmentRepo departmentRepo) {
        if (departmentRepo.findById(id).isEmpty()) {
            return false;
        }
        else return true;
    }
    public static Boolean isTheyExist(Long authorizeId, Long empId, IEmployeeRepo employeeRepo) {

        if(employeeRepo.findById(authorizeId).isPresent() &&
                employeeRepo.findById(empId).isPresent()) {
            return true;
        }
        else return false;
    }
}
