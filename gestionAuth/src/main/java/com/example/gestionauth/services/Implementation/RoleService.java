package com.example.gestionauth.services.Implementation;

import com.example.gestionauth.persistence.entity.Role;
import com.example.gestionauth.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}

