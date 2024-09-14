package com.sosnovich.cucumber_example.service;

import com.sosnovich.cucumber_example.model.OrderResponse;

public interface OrderService {
    OrderResponse getOrder(int id);
}
