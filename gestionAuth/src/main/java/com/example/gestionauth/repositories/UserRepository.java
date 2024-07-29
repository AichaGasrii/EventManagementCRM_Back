package com.example.gestionauth.repositories;

import com.example.gestionauth.persistence.entity.Role;
import com.example.gestionauth.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {
    public User findByUserEmail(String UserEmail);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);

    List<User> findByRole(String string);

    public boolean existsByUserEmail(String UserEmail);

    @Query("select u.userPassword from User u where u.userEmail=?1")
    public String getPasswordByUserEmail(String UserEmail);

    User findByVerificationToken(String token);
    User findByUserName(String username);

}
