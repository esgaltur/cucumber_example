package com.sosnovich.cucumber_example.service;

import com.sosnovich.cucumber_example.generated.model.OrderResponse;
import com.sosnovich.cucumber_example.generated.model.OrderUpdateRequest;
import com.sosnovich.cucumber_example.model.OrderModel;
import jakarta.validation.Valid;

public interface OrderService {
    OrderResponse getOrder(int id);

    OrderResponse createOrder(@Valid OrderModel orderModel);

    boolean removeOrder(int id);

    OrderResponse updateOrder(int id, OrderUpdateRequest order);

}
