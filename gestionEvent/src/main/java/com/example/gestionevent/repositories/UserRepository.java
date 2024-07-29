package com.example.gestionevent.repositories;

import com.example.gestionevent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    public User findByUserEmail(String UserEmail);


    List<User> findByRole(String string);

    public boolean existsByUserEmail(String UserEmail);

    @Query("select u.userPassword from User u where u.userEmail=?1")
    public String getPasswordByUserEmail(String UserEmail);

    User findByVerificationToken(String token);
    User findByUserName(String username);
    Optional<User> findUserByUserName(String userName);
}
