package com.ll.mb.domain.product.cart.entity;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.product.entity.Product;
import com.ll.mb.global.jpa.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class CartItem extends BaseTime {
    @ManyToOne
    private Member buyer;
    @ManyToOne
    private Product product;
}