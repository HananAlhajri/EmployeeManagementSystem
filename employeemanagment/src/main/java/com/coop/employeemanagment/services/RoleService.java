package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.repos.IRoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    @Autowired
    private final IRoleRepo roleRepository;

    public Role addRole(Role role){return roleRepository.save(role);}

    public List<Object> findAllRoles() {return Collections.singletonList(roleRepository.findAll());}
}
