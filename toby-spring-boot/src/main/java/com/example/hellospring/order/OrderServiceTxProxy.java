package com.example.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class OrderServiceTxProxy implements OrderService{
	private final OrderService target;
	private final PlatformTransactionManager transactionManager;

	public OrderServiceTxProxy (OrderService orderService, PlatformTransactionManager transactionManager) {
		this.target = orderService;
		this.transactionManager = transactionManager;
	}

	@Override
	public Order createOrder(String no, BigDecimal total) {
		return target.createOrder(no, total);
	}

	@Override
	public List<Order> createOrders (List<Order> reqs) {
		return new TransactionTemplate(transactionManager).execute(status -> {
			return target.createOrders(reqs);
		});
	}

}
