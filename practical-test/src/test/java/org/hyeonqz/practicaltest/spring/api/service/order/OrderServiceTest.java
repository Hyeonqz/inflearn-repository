package org.hyeonqz.practicaltest.spring.api.service.order;

import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.*;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.HOLD;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.SELLING;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductType.*;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductType.HANDMADE;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hyeonqz.practicaltest.spring.api.controller.order.reqeust.OrderCreateRequest;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp () {

    }

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        Product product = this.createProduct(HANDMADE, "001",1000);
        Product product2 = this.createProduct(HANDMADE, "002",1000);
        Product product3 = this.createProduct(HANDMADE, "003",1000);

        List<Product> products = List.of(product, product2, product3);
        productRepository.saveAll(products);

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001","002"))
            .build();

        // when
        orderService.createOrder(request);


        // then
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .productType(type)
            .productNumber(productNumber)
            .price(price)
            .sellingType(SELLING)
            .productName("메뉴 이름 ")
            .build();
    }

    private Product getProduct3 () {
        return Product.builder()
            .productNumber("003")
            .productType(HANDMADE)
            .sellingType(STOP_SELLING)
            .productName("카푸치노")
            .price(6000)
            .build();
    }

    private Product getProduct2 () {
        return Product.builder()
            .productNumber("002")
            .productType(HANDMADE)
            .sellingType(HOLD)
            .productName("카페라떼")
            .price(4500)
            .build();
    }

    private Product getProduct () {
        return Product.builder()
            .productNumber("001")
            .productType(HANDMADE)
            .sellingType(SELLING)
            .productName("아메리카노")
            .price(4000)
            .build();
    }

}