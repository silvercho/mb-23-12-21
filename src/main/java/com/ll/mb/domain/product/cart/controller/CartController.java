package com.ll.mb.domain.product.cart.controller;

import com.ll.mb.domain.global.exceptions.GlobalException;
import com.ll.mb.domain.product.cart.service.CartService;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.service.ProductService;
import com.ll.mb.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final Rq rq;
    private final CartService cartService;
    private final ProductService productServie;

    @PostMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public String add(
            @PathVariable long id,
            @RequestParam(defaultValue = "/") String redirectUrl
    ) {
        Product product = productServie.findById(id).orElseThrow(() -> new GlobalException("400", "존재하지 않는 상품입니다."));
        cartService.addItem(rq.getMember(), product);

        return rq.redirect(redirectUrl, null);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("isAuthenticated()")
    public String remove(
            @PathVariable long id,
            @RequestParam(defaultValue = "/") String redirectUrl
    ) {
        Product product = productServie.findById(id).orElseThrow(() -> new GlobalException("400", "존재하지 않는 상품입니다."));
        cartService.removeItem(rq.getMember(), product);

        return rq.redirect(redirectUrl, null);
    }
}