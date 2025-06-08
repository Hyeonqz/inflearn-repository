package org.hyeonqz.practicaltest.spring.api.service.order;

import java.time.LocalDate;
import java.util.List;

import org.hyeonqz.practicaltest.spring.api.service.mail.MailService;
import org.hyeonqz.practicaltest.spring.domain.order.Order;
import org.hyeonqz.practicaltest.spring.domain.order.OrderRepository;
import org.hyeonqz.practicaltest.spring.domain.order.OrderStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {
    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결제 완료된 주문들을 가져와서
        // 원래대로라면 주문 일시, 결제 완료 일시 필드가 따로 있어야 함.
        List<Order> orders = orderRepository.findOrdersBy(
            orderDate.atStartOfDay(),
            orderDate.plusDays(1).atStartOfDay(),
            OrderStatus.COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalAmount = orders.stream()
            .mapToInt(Order::getTotalPrice)
            .sum();

        // 메일 전송
        boolean result = mailService.sendMail("no-reply@naver.com",
            email,
            String.format("[주문 매출 통계] %s", orderDate),
            String.format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if(!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }
        return true;
    }
}
