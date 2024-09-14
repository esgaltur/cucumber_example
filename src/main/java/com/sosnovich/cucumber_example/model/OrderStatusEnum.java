package com.sosnovich.cucumber_example.model;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW("NEW"),

    SHIPPED("SHIPPED"),

    DELIVERED("DELIVERED"),

    CANCELLED("CANCELLED");

    private final String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }

}
