package com.sosnovich.cucumber_example.mappers;

import com.sosnovich.cucumber_example.entity.OrderEntity;
import com.sosnovich.cucumber_example.model.Order;
import com.sosnovich.cucumber_example.model.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    Order toOrder(OrderEntity order);

    OrderResponse toOrderResponse(Order order);


}
