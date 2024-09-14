package com.sosnovich.cucumber_example.controller;

import com.sosnovich.cucumber_example.api.OrdersApi;
import com.sosnovich.cucumber_example.model.OrderResponse;
import com.sosnovich.cucumber_example.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(name = "id", description = "The ID of the order to retrieve", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        OrderResponse a = orderService.getOrder(id);
        return ResponseEntity.ok(a);
    }
}
