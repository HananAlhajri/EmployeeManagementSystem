# Employee Management System
Employee Management System Using Spring Boot, Swagger and MySQL database. 

## Tools and technologies utilized: 
<li>Spring Web </li> 
<li>Spring Data JPA </li> 
<li>Lombok </li> 
<li>Swagger </li> 
<li>MySQL database</li> 

## Sql commands:
You can do the exact same thing using Swagger or Postman to send JSON. See next section.

### Department table: 
``` 
INSERT INTO `department` (`id`, `name`, `description`) VALUES
	(1, 'IT', 'Information Technology'),
	(2, 'TelComm', 'Telecommunication'),
	(3, 'Ins', 'Insurance'),
	(4, 'HR', 'Human Resources');
```

### Roles table: 
``` 
INSERT INTO `roles` (`role_id`, `name`, `description`) VALUES
	(1, 'CEO', 'Chief Executive Officer'),
	(2, 'MANAGER', 'Section Manager'),
	(3, 'EMPLOYEE', 'User');
```

### Account table: 
``` 
INSERT INTO `account` (`id`, `username`, `password`) VALUES
	(1, 'Hanan', 'example');
```

### Employee table: 
``` 
INSERT INTO `employee` (`id`, `first_name`, `father_name`, `last_name`,`date_of_birth`,`account_id` , `job_title`, `national_id`, `role_id`) VALUES
	(1, 'Hanan', 'Mohammed', 'Alhajri', '2000-01-01', 1 ,'Software Engineer', 109191, 1),
	(2, 'Renad', 'Abdullah', 'Alosaimi', '2000-01-01', 2 ,'Software Engineer', 10882, 2),
	(3, 'Maryam', 'Mohammed', 'Alsulaiman', '2000-01-01', 3 ,'Software Engineer', 298651, 3);

```

> You can also add 'Grandfather name': 
``` 
INSERT INTO `employee` (`id`, `first_name`, `father_name`,`grand_father_name`, `last_name`,`date_of_birth` ,`account_id` , `job_title`, `national_id`, `role_id`) VALUES
	(0, 'firstName', 'fatherName', 'grandFatherName', 'lastName','2000-01-01', 0 ,'JobTitle', 0 , 0);
```

## OR JSON: 
### For Department:

``` 
{
  "id": 0,
  "name": "string",
  "description": "string"
}
```

### For Roles:
``` 
{
  "id": 0,
  "name": "string",
  "description": "string"
}
```

### For Account:
``` 
{
  "id": 0,
  "username": "string",
  "password": "string"
}
```


### For Employee:
``` 
{
  "id": 0,
  "firstName": "String",
  "fatherName": "String",
  "grandFatherName": "String",
  "lastName": "String",
  "jobTitle": "String",
  "nationalID": 0,
  "dateOfBirth": "2023-01-01",
  "role": {
    "id": 0
  },
  "account": {
    "id": 0
  }
}
```


# How it looks: 
<img src = "https://user-images.githubusercontent.com/92547643/232350955-92b43d6b-ff67-4f16-b77e-9d24516bde4b.png"> 
<img src = "https://user-images.githubusercontent.com/92547643/231270364-732061c9-d3e0-41f2-b9b4-421294592d78.png">
<img src = "https://user-images.githubusercontent.com/92547643/231270482-8e2928e1-ce59-4d56-b186-45efdb10d4d3.png"><br><br>

### IMPORTANT NOTEs: 
> <li>'yourId' represent the employee doing the action. It is used to check the authority of the employee whose doing the action. <br> Example: A department can be deleted by CEO only. Therefor, the CEO enter their Id in 'yourId'.<br></li>
> <li>In employee: "/all/emp" && "/delete/{id} && getEmployeeById" AND In Account: "account/all" are used by developer to check faster. They can be removed and checked from database.<br></li>



## Methods description: 
#### For Employee
POST: /add{yourId}
> Takes yourId to authorize:
> <li>CEO: can add employee and give 'Employee' or 'Manager' role.</li>
>  <li>MANAGER: can add employee and give 'Employee' role only.</li><br>

PUT: /update/{employeeId}/{yourId}
>Takes employeeId to be update their ROLE only. And 'yourId' to authorize:<li>CEO: can assign any role.</li> <li>MANAGER: can assign an 'EMPLOYEE' role ONLY.</li><br> .<br><br>

#### For Department
POST: /add{yourId}
> Takes yourId to authorize: Only CEO can add new department.

PUT: /update/{departmentId}/{yourId}
> Takes departmentId to be updated. <br> yourId to authorize: Only CEO can update department information.

PUT: /update/assignManagerToDepartment/{departmentId}/{managerId}/{yourId}
> Takes the departmentId to see if it exsist & assign the manager by managerId. <br> yourId: Only CEO can assign managers to departments.

PUT: /update/addEmployeeInDepartment/{departmentId}/{employeeId}/{yourId}
> Takes the departmentId to see if it exsist & assign the manager by managerId. <br>
> yourId to authorize: <li>CEO: can add employee to any department.</li> <li>MANAGER: can add employee to one of the departments they manage only.</li><br>


GET: /find/{departmentId}/{yourId}
> Takes departmentId to return its information. <br> yourId to authorize:  Only CEOs and MANAGERs can view departments information.
GET: /all{yourId}
> Takes yourId to authorize: <li>CEO: can view all departments & the employees in each. </li> <li>MANAGER: can view only the departments they manage.</li><br>

GET: /ManagerDepartments{yourId}
> Takes yourId to authorize: <li>CEO: can view all departments & the employees in each. </li> <li>MANAGER: can view only the departments they manage. </li><br>

DELETE: /delete/{departmentId}/{yourId}
> Takes department Id to delete it, and yourId to authorize: only CEOs can delete a department under one Condition: <li>The department Must not contain a department manager or employees in it. </li>

<br><br>


## Done by: 
Hanan Alhajri, Maryam Alsuliman, and Renad Alosaimi.


