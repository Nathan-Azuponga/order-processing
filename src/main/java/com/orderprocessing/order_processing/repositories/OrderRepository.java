package com.orderprocessing.order_processing.repositories;


import com.orderprocessing.order_processing.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}