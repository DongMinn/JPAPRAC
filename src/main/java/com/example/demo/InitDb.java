package com.example.demo;

import com.example.demo.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void initService() {
        initService.doInit1();
        initService.doInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void doInit1() {
            Member member1 = createMember("userA", "서울", "사당", "1111");

            em.persist(member1);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);

            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 200);

            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 2);


            Delivery delivery = new Delivery();
            delivery.setAddress(member1.getAddress());

            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);

            em.persist(order);


        }

        public void doInit2() {

            Member member2 = createMember("userB", "부산", "동구", "323");

            em.persist(member2);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);

            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 30000, 300);

            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 60000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 120000, 4);


            Delivery delivery = new Delivery();
            delivery.setAddress(member2.getAddress());

            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);

            em.persist(order);


        }

        private Book createBook(String s, int i, int i2) {
            Book book1 = new Book();
            book1.setName(s);
            book1.setPrice(i);
            book1.setStockQuantity(i2);
            return book1;
        }

        private Member createMember(String userB, String 부산, String 동구, String s) {
            Member member2 = new Member();
            member2.setName(userB);
            member2.setAddress(new Address(부산, 동구, s));
            return member2;
        }

    }
}
