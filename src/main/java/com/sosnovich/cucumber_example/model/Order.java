package com.sosnovich.cucumber_example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Order {
    private int id;
    private String item;
    private int quantity;
    private BigDecimal price;
    private OrderStatusEnum status;
}
