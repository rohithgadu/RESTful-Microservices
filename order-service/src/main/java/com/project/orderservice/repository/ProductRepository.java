package com.project.orderservice.repository;

import com.project.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Order,Long> {

}
