package com.example.musicdbexam.repository;

import com.example.musicdbexam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);


    boolean existsByUsernameOrEmail(String username, String email);
}
