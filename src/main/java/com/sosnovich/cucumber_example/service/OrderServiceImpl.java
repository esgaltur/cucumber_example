package com.sosnovich.cucumber_example.service;

import com.sosnovich.cucumber_example.repository.OrderRepository;
import com.sosnovich.cucumber_example.mappers.OrderMapper;
import com.sosnovich.cucumber_example.model.OrderResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }


    public OrderResponse getOrder(int id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        var model = orderMapper.toOrder(order);
        return orderMapper.toOrderResponse(model);
    }

}
