package com.sosnovich.cucumber_example.entity;

import com.sosnovich.cucumber_example.model.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "Orders")
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private Integer quantity;
    @Column(precision = 10, scale = 2) // precision - общее количество цифр, scale - количество знаков после запятой
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;


}
