package org.hyeonqz.practicaltest.spring.domain.order;

import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("상품 리스트에서 주문의 총 금액을 계산한다.")
    void calculateTotalPrice () {
        // given
        List<Product> product = List.of(
            this.createProduct("001", 1000),
            this.createProduct("002", 2000)
        );

        // when
        LocalDateTime now = LocalDateTime.now();
        Order order = Order.create(product, now);

        // then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getStatus()).isEqualByComparingTo(OrderStatus.INIT);

    }

    @Test
    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
    void init () {
        // given
        LocalDateTime now = LocalDateTime.now();
        List<Product> product = List.of(
            this.createProduct("001", 1000),
            this.createProduct("002", 2000)
        );

        // when
        Order order = Order.create(product, now);

        // then
        Assertions.assertThat(order.getRegisteredDateTime()).isEqualTo(now);

    }

    private Product createProduct (String productNumber, int price) {
        return Product.builder()
            .productType(ProductType.HANDMADE)
            .productNumber(productNumber)
            .price(price)
            .sellingType(SELLING)
            .productName("메뉴 이름 ")
            .build();
    }

}