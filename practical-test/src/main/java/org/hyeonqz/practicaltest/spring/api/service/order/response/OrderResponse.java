package org.hyeonqz.practicaltest.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;

import org.hyeonqz.practicaltest.spring.api.service.product.response.ProductResponse;
import org.hyeonqz.practicaltest.spring.domain.order.Order;
import org.hyeonqz.practicaltest.spring.domain.order.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

    private Long id;
    private OrderStatus status;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    public OrderResponse (Long id, OrderStatus status, int totalPrice, LocalDateTime registeredDateTime,
        List<ProductResponse> products) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .status(order.getStatus())
            .totalPrice(order.getTotalPrice())
            .registeredDateTime(order.getRegisteredDateTime())
            .products(order.getOrderProducts().stream()
                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                .toList()
            )
            .build();
    }

}
