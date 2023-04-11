package com.coop.employeemanagment.infrastructures.constans;

public class APIs {
    private APIs(){}
    public static class Employee {
        public static final String baseUrl = "/employee";
        public static final String getAllEmployee = "/all";
        public static final String getEmployeeById = "/find";
        public static final String addEmployee = "/add";
        public static final String updateEmployee = "/update";
        public static final String deleteEmployee = "/delete";
    }

    public static class Department {
        public static final String baseUrl = "/department";
        public static final String getAllDepartments = "/all";
        public static final String getManagerDepartments = "/ManagerDepartments";
        public static final String getSpecificDepartment = "/find";
        public static final String addDepartment = "/add";
        public static final String updateDepartment = "/update";
        public static final String deleteDepartment = "/delete";
    }

    public static class Role{
        public static final String baseUrl = "/role";
        public static final String addRole = "/add";
        public static final String allRoles = "/all";
    }

    public static class Account{
        public static final String baseUrl = "/account";
        public static final String AddAccount = "/add";
        public static final String allAccounts = "/all";
        public static final String login = "/login";
        public static final String logout = "/logout";
    }
}
