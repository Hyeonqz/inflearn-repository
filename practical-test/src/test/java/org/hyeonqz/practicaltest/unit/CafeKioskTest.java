package org.hyeonqz.practicaltest.unit;

import org.hyeonqz.practicaltest.unit.beverage.Americano;
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

}