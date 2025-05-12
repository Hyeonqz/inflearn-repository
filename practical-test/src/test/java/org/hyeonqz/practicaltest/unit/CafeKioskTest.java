package org.hyeonqz.practicaltest.unit;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.hyeonqz.practicaltest.unit.beverage.Americano;
import org.hyeonqz.practicaltest.unit.beverage.Latte;
import org.hyeonqz.practicaltest.unit.order.Order;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

	@Test
	void add_test() {
	    // given
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

	    // when
		System.out.println(">>> 음료수 개수 : " + cafeKiosk.getBeverages().size());
		System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
	    // then
	}

	@Test
	void add_testV2() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		Assertions.assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
		Assertions.assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void add_count_test() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano, 2);

		Assertions.assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
		Assertions.assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
	}

	@Test
	void add_count_throw_test() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		Assertions.assertThatThrownBy(() -> cafeKiosk.add(americano,0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
	}


	@Test
	void remove_test() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);
		Assertions.assertThat(cafeKiosk.getBeverages()).hasSize(1);

		cafeKiosk.remove(americano);
		Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	void clear_test() {
		// given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);

		// when
		cafeKiosk.clear();

		// then
		Assertions.assertThat(cafeKiosk.getBeverages()).isEmpty();
	}

	@Test
	void createOrderV2() {
		// given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		cafeKiosk.add(americano);

		Order order = cafeKiosk.createOrder();

		Assertions.assertThat(order.getBeverages()).hasSize(1);
		Assertions.assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
	}

	@Test
	void createOrderV3_currenTime() {
		// given
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();

		LocalDateTime currentDateTime = LocalDateTime.of(2025,5,2,9,59);

		Assertions.assertThatThrownBy(() -> cafeKiosk.createOrder(currentDateTime))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("주문시간이 아닙니다. 관리자에게 문의해주세요");
	}















}
