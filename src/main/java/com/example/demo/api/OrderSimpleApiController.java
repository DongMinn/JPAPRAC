package com.example.demo.api;

import com.example.demo.api.response.Result;
import com.example.demo.api.response.SimpleOrderDto;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * xToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            order.getMember().getName();// Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result<List<SimpleOrderDto>> orderV2() {

        //Order 2개
        //N+1 > 1 + N(2)
        // 1(order한번 가져오고)
        // N (한번은 회원 정보 ) + N(한번은 딜리버리 정보)

        List<Order> orders = orderRepository.findAll();
        List<SimpleOrderDto> simpleOrderDtos = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
        return new Result<>((long) simpleOrderDtos.size(), simpleOrderDtos);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result<List<SimpleOrderDto>> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<SimpleOrderDto> simpleOrderDtos = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
        return new Result<>((long) simpleOrderDtos.size(), simpleOrderDtos);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result<List<SimpleOrderDto>> orderV4() {

        List<SimpleOrderDto> simpleOrderDtos = orderRepository.findOrderDtos();
        return new Result<>((long) simpleOrderDtos.size(), simpleOrderDtos);
    }

}
