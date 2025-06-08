package org.hyeonqz.practicaltest.spring.api.service.order;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hyeonqz.practicaltest.spring.domain.history.MailSendHistory;
import org.hyeonqz.practicaltest.spring.domain.history.MailSendHistoryRepository;
import org.hyeonqz.practicaltest.spring.domain.order.Order;
import org.hyeonqz.practicaltest.spring.domain.order.OrderRepository;
import org.hyeonqz.practicaltest.spring.domain.order.OrderStatus;
import org.hyeonqz.practicaltest.spring.domain.orderproduct.OrderProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @AfterEach
    void tearDown () {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    //@Transactional
    @DisplayName("결제완료 주문들의 조회하여 매출 통계 메일을 전송한다.")
    void sendOrderStatisticsMail () {
        // given
        LocalDateTime now = LocalDateTime.of(2025, 6, 7, 0, 0,0);

        Product product1 = this.createProduct("001", ProductType.HANDMADE, ProductSellingType.SELLING, "아메리카노", 4000);
        Product product2 = this.createProduct("002", ProductType.HANDMADE, ProductSellingType.HOLD, "카페라떼", 4500);
        Product product3 = this.createProduct("003", ProductType.HANDMADE, ProductSellingType.STOP_SELLING, "팥빙수", 7000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = getCreatePaymentCompletedOrder(products, LocalDateTime.of(2025,6,6,23,59,59));
        Order order2 = getCreatePaymentCompletedOrder(products, now);
        Order order3 = getCreatePaymentCompletedOrder(products, LocalDateTime.of(2025,6,7,23,59,59));
        Order order4 = getCreatePaymentCompletedOrder(products, LocalDateTime.of(2025,6,8,0,0,0));
        orderRepository.saveAll(List.of(order1, order2, order3));

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2025, 6, 7), "no-reply@naver.com");

        // then
        Assertions.assertThat(result).isTrue();

        List<MailSendHistory> historyRepositoryAll = mailSendHistoryRepository.findAll();
        Assertions.assertThat(historyRepositoryAll).hasSize(1)
            .extracting("content")
            .contains("총 매출 합계는 18000원 입니다.");
    }

    private Order getCreatePaymentCompletedOrder (List<Product> products,  LocalDateTime now) {
        return Order.builder()
            .products(products)
            .status(OrderStatus.PAYMENT_COMPLETED)
            .registeredDateTime(now)
            .build();
    }

    private Product createProduct (String productNumber, ProductType type, ProductSellingType sellingType, String name,
        int price) {
        return Product.builder()
            .productNumber(productNumber)
            .productType(type)
            .sellingType(sellingType)
            .productName(name)
            .price(price)
            .build();
    }

}