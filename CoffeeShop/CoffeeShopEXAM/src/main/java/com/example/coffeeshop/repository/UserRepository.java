package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.entity.User;
import com.example.coffeeshop.model.service.UserServiceModel;
import com.example.coffeeshop.model.view.UserViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    //CREATE BIDIRECTIONAL RELATION WITH ORDERS(add Set<Order> in User.class)
    @Query("SELECT u FROM User u ORDER BY size(u.orders) DESC")
    List<User> findAllByCountOfOrdersDesc();

    boolean existsByUsernameOrEmail(String username, String email);
}
