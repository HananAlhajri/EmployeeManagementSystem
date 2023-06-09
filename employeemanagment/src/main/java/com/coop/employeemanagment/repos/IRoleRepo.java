package com.coop.employeemanagment.repos;

import com.coop.employeemanagment.infrastructures.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<Role, Integer> {

}
