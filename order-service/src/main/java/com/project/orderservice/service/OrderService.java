package com.project.orderservice.service;


import com.project.orderservice.dto.InventoryResponse;
import com.project.orderservice.dto.OrderItemsDto;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderItems;
import com.project.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WebClient.Builder webClient;

    public void placeOrder(OrderRequest  orderRequest){
        Order order=Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItemsList(orderRequest.getOrderItemsDtoList()
                        .stream()
                        .map(orderItemsDto -> mapToDto(orderItemsDto))
                        .toList())
                .build();

        List<String> skuCodes = order.getOrderItemsList()
                .stream()
                .map(orderItems -> orderItems.getSkuCode())
                .toList();

        InventoryResponse[] inventoryResponses= webClient.build().get()
                .uri("http://inventory-service/api/inventory/",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock =Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse -> InventoryResponse.isInStock());

        if(allProductsInStock)
            productRepository.save(order);
        else{
            throw new IllegalArgumentException("Product not in stock");
        }

    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        return OrderItems.builder()
                .price(orderItemsDto.getPrice())
                .quantity(orderItemsDto.getQuantity())
                .skuCode(orderItemsDto.getSkuCode())
                .build();
    }


}
