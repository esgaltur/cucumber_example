package com.sosnovich.cucumber_example.controller;

import com.sosnovich.cucumber_example.generated.api.OrdersApi;
import com.sosnovich.cucumber_example.generated.model.OrderCreateRequest;
import com.sosnovich.cucumber_example.generated.model.OrderResponse;
import com.sosnovich.cucumber_example.generated.model.OrderUpdateRequest;
import com.sosnovich.cucumber_example.mappers.OrderMapper;
import com.sosnovich.cucumber_example.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class OrderController implements OrdersApi {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderById(@Parameter(name = "id", description = "The ID of the order to retrieve", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id) {
        OrderResponse a = orderService.getOrder(id);
        return ResponseEntity.ok(a);
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(@Parameter(name = "OrderCreateRequest", description = "The order details (including ID if specified)", required = true) @Valid @RequestBody OrderCreateRequest orderCreateRequest) {

         var orderModel = orderMapper.toOrder(orderCreateRequest);

        var orderResponse = orderService.createOrder(orderModel);
        return ResponseEntity.created(URI.create("/order/".concat(orderResponse.getId().toString()))).build();
    }

    @Override
    public ResponseEntity<Void> deleteOrderById(Integer id) {
        var removeOrder = orderService.removeOrder(id);
        if (removeOrder) {
            return ResponseEntity.ok().build();
        }
        throw new EntityNotFoundException("Order with id " + id + " not found");
    }

    @Override
    public ResponseEntity<OrderResponse> updateOrder(Integer id, OrderUpdateRequest order) {
        var orderResponse = orderService.updateOrder(id, order);
        return ResponseEntity.ok(orderResponse);
    }
}
