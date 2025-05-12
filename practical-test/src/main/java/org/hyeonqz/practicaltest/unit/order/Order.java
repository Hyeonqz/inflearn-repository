package org.hyeonqz.practicaltest.unit.order;

import java.time.LocalDateTime;
import java.util.List;

import org.hyeonqz.practicaltest.unit.beverage.Beverage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Order {

	private final LocalDateTime orderDate;
	private final List<Beverage> beverages;

	public Order (LocalDateTime orderDate, List<Beverage> beverages) {
		this.orderDate = orderDate;
		this.beverages = beverages;
	}

	public LocalDateTime getOrderDate () {
		return orderDate;
	}

	public List<Beverage> getBeverages () {
		return beverages;
	}

}
