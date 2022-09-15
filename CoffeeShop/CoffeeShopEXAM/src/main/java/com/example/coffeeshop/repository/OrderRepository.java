package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.entity.Order;
import com.example.coffeeshop.model.view.OrderViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByPriceDesc();
}
