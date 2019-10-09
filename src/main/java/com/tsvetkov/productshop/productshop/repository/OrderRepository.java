package com.tsvetkov.productshop.productshop.repository;

import com.tsvetkov.productshop.productshop.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllOrdersByCustomer_UsernameOrderByFinishedOn(String username);
}
