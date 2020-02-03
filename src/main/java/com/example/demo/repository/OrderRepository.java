package com.example.demo.repository;


import com.example.demo.api.response.SimpleOrderDto;
import com.example.demo.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            "SELECT o from Order o " +
                    "join fetch o.member m " +
                    "join fetch o.delivery d "
    )
    List<Order> findAllWithMemberDelivery();


    @Query(
            "select new com.example.demo.api.response.SimpleOrderDto(o.id ,m.name, o.orderDate, o.orderStatus , d.address) " +
                    "from Order o " +
                    "join o.member m " +
                    "join o.delivery d "
    )
    List<SimpleOrderDto> findOrderDtos();

    @Query(
            "select  distinct o from Order o " +
                    "join fetch o.member m " +
                    "join fetch o.delivery d " +
                    "join fetch o.orderItems oi " +
                    "join fetch  oi.item i "
    )
    List<Order> findAllWithItem();
}
