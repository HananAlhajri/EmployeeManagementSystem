package com.coop.employeemanagment.services;

import com.coop.employeemanagment.infrastructures.entity.Role;
import com.coop.employeemanagment.repos.IRoleRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {
    private final IRoleRepo roleRepository;

    public Role addRole(Role role){return roleRepository.save(role);}

    public List<List<Role>> findAllRoles() {return Collections.singletonList(roleRepository.findAll());}
}
