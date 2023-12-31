package com.ll.mb.domain.product.cart.service;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.cart.entity.CartItem;
import com.ll.mb.domain.product.cart.repository.CartItemRepository;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.global.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addItem(Member buyer, Product product) {
        if (buyer.has(product))
            throw new GlobalException("400-1", "이미 구매한 상품 입니다.");

        CartItem cartItem = CartItem.builder()
                .buyer(buyer)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return cartItem;
    }

    @Transactional
    public void removeItem(Member buyer, Product product) {
        cartItemRepository.deleteByBuyerAndProduct(buyer, product);
    }

    public List<CartItem> findByBuyer(Member buyer) {
        return cartItemRepository.findByBuyer(buyer);
    }

    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public boolean canAdd(Member buyer, Product product) {
        if (buyer == null) return false;

        return !cartItemRepository.existsByBuyerAndProduct(buyer, product);
    }

    public boolean canRemove(Member buyer, Product product) {
        if (buyer == null) return false;

        return cartItemRepository.existsByBuyerAndProduct(buyer, product);
    }
}