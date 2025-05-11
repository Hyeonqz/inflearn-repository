package org.hyeonqz.practicaltest.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hyeonqz.practicaltest.unit.beverage.Beverage;
import org.hyeonqz.practicaltest.unit.order.Order;

import lombok.Getter;

@Getter
public class CafeKiosk {
	private final List<Beverage> beverages = new ArrayList<>();

	public void add (Beverage beverage) {
		beverages.add(beverage);
	}

	public int calculateTotalPrice () {
		int totalPrice = 0;
		for(Beverage beverage : beverages) {
			totalPrice += beverage.gerPrice();
		}
		return totalPrice;
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	// kiosk 를 통해 주문을 한다.
	public Order createOrder() {
		return new Order(LocalDateTime.now(), beverages);
	}

}
