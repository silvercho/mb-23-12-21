package com.ll.mb.domain.product.order.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<Order> search(Member buyer, Boolean payStatus, Boolean cancelStatus, Boolean refundStatus, Pageable pageable);
}