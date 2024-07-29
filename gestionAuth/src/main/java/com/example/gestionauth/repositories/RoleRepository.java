package com.example.gestionauth.repositories;

import com.example.gestionauth.persistence.entity.Role;
import com.example.gestionauth.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
