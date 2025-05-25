package org.hyeonqz.practicaltest.spring.api.service.order;

import static org.hyeonqz.practicaltest.spring.domain.product.ProductSellingType.SELLING;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductType.*;
import static org.hyeonqz.practicaltest.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hyeonqz.practicaltest.spring.api.controller.order.reqeust.OrderCreateRequest;
import org.hyeonqz.practicaltest.spring.api.service.order.response.OrderResponse;
import org.hyeonqz.practicaltest.spring.domain.order.OrderRepository;
import org.hyeonqz.practicaltest.spring.domain.orderproduct.OrderProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.hyeonqz.practicaltest.spring.domain.stock.Stock;
import org.hyeonqz.practicaltest.spring.domain.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp () {

    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Product product = this.createProduct(HANDMADE, "001",1000);
        Product product2 = this.createProduct(HANDMADE, "002",3000);
        Product product3 = this.createProduct(HANDMADE, "003",5000);

        List<Product> products = List.of(product, product2, product3);
        productRepository.saveAll(products);

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001","002"))
            .build();

        // when
        OrderResponse response = orderService.createOrder(request, now);

        // then
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
                .contains(now, 4000);
        Assertions.assertThat(response.getProducts()).hasSize(2);
    }

    @Test
    @DisplayName("중복되는 상품번호 리스트로 주문을 생성할 수 있다.")
    void createOrderWithDuplicateProducts() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Product product = this.createProduct(HANDMADE, "001",1000);
        Product product2 = this.createProduct(HANDMADE, "002",3000);
        Product product3 = this.createProduct(HANDMADE, "003",5000);

        List<Product> products = List.of(product, product2, product3);
        productRepository.saveAll(products);

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001","001"))
            .build();

        // when
        OrderResponse response = orderService.createOrder(request, now);

        // then
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response)
            .extracting("registeredDateTime", "totalPrice")
            .contains(now, 2000);
        Assertions.assertThat(response.getProducts()).hasSize(2);
    }

    @Test
    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    void createOrderWithStock() {
        // given
        LocalDateTime now = LocalDateTime.now();

        Product product = this.createProduct(BAKERY, "001",1000);
        Product product2 = this.createProduct(BOTTLE, "002",3000);
        Product product3 = this.createProduct(HANDMADE, "003",5000);
        productRepository.saveAll(List.of(product, product2, product3));

        Stock stock1 = Stock.create("001",2);
        Stock stock2 = Stock.create("002",2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001","001","002","003"))
            .build();

        // when
        OrderResponse response = orderService.createOrder(request, now);

        // then
        Assertions.assertThat(response.getProducts()).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                Tuple.tuple("001",1000),
                Tuple.tuple("001",1000),
                Tuple.tuple("002",3000),
                Tuple.tuple("003",5000)
            );

        List<Stock> stocks = stockRepository.findAll();
        Assertions.assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                Tuple.tuple("001",0),
                Tuple.tuple("002",1)
            );
    }

    @Test
    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    void createOrderWithNoStock() {
        // given
        LocalDateTime now = LocalDateTime.now();

        Product product = this.createProduct(BAKERY, "001",1000);
        Product product2 = this.createProduct(BOTTLE, "002",3000);
        Product product3 = this.createProduct(HANDMADE, "003",5000);
        productRepository.saveAll(List.of(product, product2, product3));

        Stock stock1 = Stock.create("001",1);
        Stock stock2 = Stock.create("002",1);
        stock1.deductQuantity(1);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001","001","002","003"))
            .build();

        // when & then
        Assertions.assertThatThrownBy(() -> orderService.createOrder(request, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족한 상품이 있습니다.");

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

}