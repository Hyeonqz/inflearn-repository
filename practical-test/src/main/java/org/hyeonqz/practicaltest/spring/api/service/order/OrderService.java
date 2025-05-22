package org.hyeonqz.practicaltest.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hyeonqz.practicaltest.spring.api.controller.order.reqeust.OrderCreateRequest;
import org.hyeonqz.practicaltest.spring.api.service.order.response.OrderResponse;
import org.hyeonqz.practicaltest.spring.domain.order.Order;
import org.hyeonqz.practicaltest.spring.domain.order.OrderRepository;
import org.hyeonqz.practicaltest.spring.domain.product.Product;
import org.hyeonqz.practicaltest.spring.domain.product.ProductRepository;
import org.hyeonqz.practicaltest.spring.domain.product.ProductType;
import org.hyeonqz.practicaltest.spring.domain.stock.Stock;
import org.hyeonqz.practicaltest.spring.domain.stock.StockRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Transactional
    public OrderResponse createOrder (OrderCreateRequest request, LocalDateTime now) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> duplicateProducts = this.findProductsBy(productNumbers);

        // 재고 차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers = duplicateProducts.stream()
            .filter(product -> ProductType.containsStockType(product.getProductType()))
            .map(Product::getProductNumber)
            .toList();

        // 재고 엔티티 조회
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

        Map<String, Stock> stockMap = stocks.stream()
            .collect(Collectors.toMap(Stock::getProductNumber, stock -> stock));

        // 상품별 counting
        Map<String, Long> productCountingMap = stockProductNumbers.stream()
            .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        // 재고 차감 시도
        for (String stockProductNumber : stockProductNumbers) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if(stock.isQuantityLessThan(quantity))
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");

            stock.deductQuantity(quantity);
        }

        Order order = Order.create(duplicateProducts, now);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy (List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
            .map(productMap::get)
            .toList();
    }

}
