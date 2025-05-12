package org.hyeonqz.practicaltest.unit;

import java.time.LocalDateTime;

import org.hyeonqz.practicaltest.unit.beverage.Americano;
import org.hyeonqz.practicaltest.unit.beverage.Latte;
import org.hyeonqz.practicaltest.unit.order.Order;

public class CafeKioskRunner {

	public static void main (String[] args) {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>> 아메리카노 추가");

		cafeKiosk.add(new Latte());
		System.out.println(">>> 라떼 추가");

		int totalPrice = cafeKiosk.calculateTotalPrice();
		System.out.println("총 주문 가격: " + totalPrice + "원");

		Order order = cafeKiosk.createOrder(LocalDateTime.now());
	}
}
