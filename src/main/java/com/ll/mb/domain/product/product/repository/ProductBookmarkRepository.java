package com.ll.mb.domain.product.product.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.entity.ProductBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBookmarkRepository extends JpaRepository<ProductBookmark, Long> {
    boolean existsByMemberAndProduct(Member actor, Product product);
}