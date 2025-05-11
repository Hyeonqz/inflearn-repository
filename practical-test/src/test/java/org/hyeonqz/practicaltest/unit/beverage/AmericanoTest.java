package org.hyeonqz.practicaltest.unit.beverage;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AmericanoTest {

	@Test
	@DisplayName("")
	void name_test() {
	    // given
		Beverage americano = new Americano();

	    // when

	    // then

		// Junit5
		assertEquals(americano.getName(), "아메리카노");
		// assertj
		Assertions.assertThat(americano.getName()).isEqualTo("아메리카노");
	}

	@Test
	@DisplayName("")
	void price_test() {
	    // given
		Beverage americano = new Americano();

		// when

	    // then
		Assertions.assertThat(americano.gerPrice()).isEqualTo(4000);
	}

}