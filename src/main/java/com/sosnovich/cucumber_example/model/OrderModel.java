package com.sosnovich.cucumber_example.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Validated
public class OrderModel {
    @Positive(message = "ID must be a positive number")
    private Integer id;
    @NotBlank(message = "Item cannot be empty")
    private String item;
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;
    @NotNull(message = "Status is required")
    private OrderStatusEnum status;
}
