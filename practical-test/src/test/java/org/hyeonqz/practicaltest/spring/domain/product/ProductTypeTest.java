package org.hyeonqz.practicaltest.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입이 아니면 False 리턴한다.")
    void isNotContainsStockType() {
        // given
        ProductType handmade = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(handmade);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType() {
        // given
        ProductType bakery = ProductType.BAKERY;

        // when
        boolean result = ProductType.containsStockType(bakery);

        // then
        Assertions.assertThat(result).isTrue();
    }

}