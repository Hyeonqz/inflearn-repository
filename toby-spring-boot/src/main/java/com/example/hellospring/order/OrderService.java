package com.example.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

public interface OrderService {
	Order createOrder(String no, BigDecimal total);
	List<Order> createOrders(List<Order> reqs);
}
