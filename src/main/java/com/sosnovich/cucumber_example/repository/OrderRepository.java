package com.sosnovich.cucumber_example.repository;

import com.sosnovich.cucumber_example.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
