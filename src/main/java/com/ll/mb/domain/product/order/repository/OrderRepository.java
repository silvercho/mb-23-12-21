package com.ll.mb.domain.product.order.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerOrderByIdDesc(Member buyer);

    List<Order> findByBuyerAndPayDateIsNotNullOrderByIdDesc(Member buyer);

    List<Order> findByBuyerAndCancelDateIsNotNullOrderByIdDesc(Member buyer);

    List<Order> findByBuyerAndRefundDateIsNotNullOrderByIdDesc(Member buyer);
}