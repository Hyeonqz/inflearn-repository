package org.hyeonqz.practicaltest.spring.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hyeonqz.practicaltest.spring.domain.BaseEntity;
import org.hyeonqz.practicaltest.spring.domain.orderproduct.OrderProduct;
import org.hyeonqz.practicaltest.spring.domain.product.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="orders")
@Entity
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    private Order (OrderStatus status, LocalDateTime registeredDateTime, List<Product> products) {
        this.status = status;
        this.totalPrice = products.stream()
            .mapToInt(Product::getPrice)
            .sum();
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .toList();
    }


    public static Order create (List<Product> products, LocalDateTime now) {
        return Order.builder()
            .status(OrderStatus.INIT)
            .products(products)
            .registeredDateTime(now)
            .build();
    }

}
