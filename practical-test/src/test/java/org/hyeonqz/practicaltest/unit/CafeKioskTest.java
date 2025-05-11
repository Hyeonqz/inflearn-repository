package org.hyeonqz.practicaltest.unit;

import org.assertj.core.api.Assertions;
import org.hyeonqz.practicaltest.unit.beverage.Americano;
import org.hyeonqz.practicaltest.unit.beverage.Latte;
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
}