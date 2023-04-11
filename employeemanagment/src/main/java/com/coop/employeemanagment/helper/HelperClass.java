package com.coop.employeemanagment.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import com.coop.employeemanagment.repos.IEmployeeRepo;
import com.coop.employeemanagment.repos.IDepartmentRepo;

public class HelperClass {
    private static IDepartmentRepo departmentRepo;
    private static IEmployeeRepo employeeRepo;

    public HelperClass() {
    }

    //helper methods
    public static String isEmployeeExists(Long id) {
        if (employeeRepo.findById(id).isEmpty()) {
            return "Employee with "+id+" does not exist";
        }
        else return "Yes";
    }

    public static String isDepartmentExists(Long id) {
        if (departmentRepo.findById(id).isEmpty()) {
            return "Department with "+id+" does not exist";
        }
        else return "Yes";
    }
    public static boolean isTheyExist(Long authorizeId, Long empId) {

        if(employeeRepo.findById(authorizeId).isPresent() &&
                employeeRepo.findById(empId).isPresent())
            return true;
        else
            return false;

    }



}
