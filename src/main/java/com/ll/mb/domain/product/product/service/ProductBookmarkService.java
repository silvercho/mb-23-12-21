package com.ll.mb.domain.product.product.service;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.entity.ProductBookmark;
import com.ll.mb.domain.product.product.repository.ProductBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductBookmarkService {
    private final ProductBookmarkRepository productBookmarkRepository;

    @Transactional
    public void createProductBookmark(Member member, Product product) {
        ProductBookmark productBookmark = ProductBookmark.builder()
                .member(member)
                .product(product)
                .build();

        productBookmarkRepository.save(productBookmark);
    }

    public boolean canBookmark(Member actor, Product product) {
        if (actor == null) return false;

        return !productBookmarkRepository.existsByMemberAndProduct(actor, product);
    }

    public boolean canCancelBookmark(Member actor, Product product) {
        if (actor == null) return false;

        return productBookmarkRepository.existsByMemberAndProduct(actor, product);
    }
}