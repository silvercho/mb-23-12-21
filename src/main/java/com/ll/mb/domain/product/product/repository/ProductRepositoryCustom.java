package com.ll.mb.domain.product.product.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> search(Member maker, Boolean published, List<String> kwTypes, String kw, Pageable pageable);
}
