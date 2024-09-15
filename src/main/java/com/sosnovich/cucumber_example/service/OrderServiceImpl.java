package com.sosnovich.cucumber_example.service;

import com.sosnovich.cucumber_example.entity.OrderEntity;

import com.sosnovich.cucumber_example.generated.model.OrderResponse;
import com.sosnovich.cucumber_example.generated.model.OrderUpdateRequest;
import com.sosnovich.cucumber_example.model.OrderModel;
import com.sosnovich.cucumber_example.repository.OrderRepository;
import com.sosnovich.cucumber_example.mappers.OrderMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

@Override
    public OrderResponse getOrder(int id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        var model = orderMapper.toOrder(order);
        return orderMapper.toOrderResponse(model);
    }

    @Override
    public OrderResponse createOrder(@Valid OrderModel orderModel) {
        var orderEntity = orderMapper.toOrderEntity(orderModel);
        var b = orderRepository.save(orderEntity);
        var c = orderMapper.toOrder(b);
        return orderMapper.toOrderResponse(c);
    }

    @Override
    public boolean removeOrder(int id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public OrderResponse updateOrder(int id, OrderUpdateRequest order) {
        // Retrieve the existing order from the database
        OrderEntity existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        var existingOrderModel = orderMapper.toOrder(existingOrder);
        orderMapper.updateOrderFromRequest(order, existingOrderModel);

        var orderEntity = orderRepository.save(orderMapper.toOrderEntity(existingOrderModel));

        var orderEntityModel = orderMapper.toOrder(orderEntity);
        // Save the updated entity back to the database
        return orderMapper.toOrderResponse(orderEntityModel);
    }

}
