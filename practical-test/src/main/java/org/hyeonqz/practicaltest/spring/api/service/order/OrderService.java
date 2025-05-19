package org.hyeonqz.practicaltest.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.hyeonqz.practicaltest.spring.api.controller.order.reqeust.OrderCreateRequest;
import org.hyeonqz.practicaltest.spring.api.service.order.response.OrderResponse;
import org.hyeonqz.practicaltest.spring.domain.order.Order;
import org.hyeonqz.practicaltest.spring.domain.order.OrderRepository;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder (OrderCreateRequest request, LocalDateTime now) {
        List<String> productNumbers = request.getProductNumbers();

        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, now);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

}
