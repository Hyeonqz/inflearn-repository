package org.hyeonqz.practicaltest.spring.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findOrdersBy () {
        orderRepository.findOrdersBy(LocalDateTime.now(), LocalDateTime.now(), OrderStatus.COMPLETED);
    }

}