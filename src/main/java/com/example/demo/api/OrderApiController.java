package com.example.demo.api;

import com.example.demo.api.response.OrderDto;
import com.example.demo.api.response.Result;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.entity.Order;
import com.example.demo.repository.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public Result<List<Order>> ordersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }

        return new Result<List<Order>>((long) all.size(), all);
    }

    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> orderV2(){

        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos =  orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>((long)orderDtos.size(), orderDtos);
    }

    @GetMapping("/api/v3/orders")
    public Result<List<OrderDto>> orderV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> orderDtos =  orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>((long)orderDtos.size(), orderDtos);
    }

    @GetMapping("/api/v3.1/orders")
    public Result<List<OrderDto>> orderV3_page(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> orderDtos =  orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new Result<>((long)orderDtos.size(), orderDtos);
    }
}
