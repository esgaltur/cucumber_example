package com.sosnovich.cucumber_example.mappers;

import com.sosnovich.cucumber_example.entity.OrderEntity;
import com.sosnovich.cucumber_example.generated.model.OrderCreateRequest;
import com.sosnovich.cucumber_example.generated.model.OrderResponse;
import com.sosnovich.cucumber_example.generated.model.OrderUpdateRequest;
import com.sosnovich.cucumber_example.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {


    OrderModel toOrder(OrderCreateRequest orderCreateRequest);

    OrderResponse toOrderResponse(OrderModel orderModel);


    OrderModel toOrder(OrderEntity order);

    OrderEntity toOrderEntity(OrderModel order);

    void updateOrderFromRequest(OrderUpdateRequest request, @MappingTarget OrderModel entity);

}
