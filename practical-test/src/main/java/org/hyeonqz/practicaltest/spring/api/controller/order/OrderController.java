package org.hyeonqz.practicaltest.spring.api.controller.order;

import java.time.LocalDateTime;

import org.hyeonqz.practicaltest.spring.api.ApiResponse;
import org.hyeonqz.practicaltest.spring.api.controller.order.reqeust.OrderCreateRequest;
import org.hyeonqz.practicaltest.spring.api.service.order.OrderService;
import org.hyeonqz.practicaltest.spring.api.service.order.response.OrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder (@RequestBody OrderCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request, now));
    }

}
