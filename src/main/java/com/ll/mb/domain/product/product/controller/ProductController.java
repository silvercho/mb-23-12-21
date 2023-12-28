package com.ll.mb.domain.product.product.controller;

import com.ll.mb.domain.global.exceptions.GlobalException;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.domain.product.product.service.ProductService;
import com.ll.mb.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final Rq rq;
    private final ProductService productService;

    @GetMapping("/list")
    public String list(
            @RequestParam(value = "kwType", defaultValue = "name") List<String> kwTypes,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Product> itemsPage = productService.search(null, true, kwTypes, kw, pageable);
        model.addAttribute("itemPage", itemsPage);
        model.addAttribute("kwTypesMap", kwTypesMap);
        model.addAttribute("page", page);

        return "domain/product/product/list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@PathVariable long id) {
        return null;
    }
    @PostMapping("/{id}/bookmark")
    @PreAuthorize("isAuthenticated()")
    public String bookmark(
            @PathVariable long id,
            @RequestParam(defaultValue = "/") String redirectUrl
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new GlobalException("400", "존재하지 않는 상품입니다."));
        productService.bookmark(rq.getMember(), product);

        return rq.redirect(redirectUrl, null);
    }
}