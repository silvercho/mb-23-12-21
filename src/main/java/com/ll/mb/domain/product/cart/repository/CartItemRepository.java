package com.ll.mb.domain.product.cart.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuyer(Member buyer);
}