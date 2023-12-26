package com.ll.mb.domain.product.order.entity;

import com.ll.mb.domain.global.exceptions.GlobalException;
import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.cart.entity.CartItem;
import com.ll.mb.global.app.AppConfig;
import com.ll.mb.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "order_")
public class Order extends BaseEntity {
    @ManyToOne
    private Member buyer;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime payDate; // 결제일
    private LocalDateTime cancelDate; // 취소일
    private LocalDateTime refundDate; // 환불일

    public void addItem(CartItem cartItem) {

        if (buyer.has(cartItem.getProduct()))
            throw new GlobalException("400-1", "이미 구매한 상품입니다.");

        OrderItem orderItem = OrderItem.builder()
                .order(this)
                .product(cartItem.getProduct())
                .build();

        orderItems.add(orderItem);
    }
    public long calcPayPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getPayPrice)
                .sum();
    }

    public void setPaymentDone() {
        payDate = LocalDateTime.now();
        orderItems.stream()
                .forEach(OrderItem::setPaymentDone);
    }

    public void setCancleDone() {
        cancelDate = LocalDateTime.now();
        orderItems.stream()
                .forEach(OrderItem::setCancelDone);
    }
    public void setRefundDone() {
        refundDate = LocalDateTime.now();
        orderItems.stream()
                .forEach(OrderItem::setRefundDone);
    }
    public String getName() {
        String name = orderItems.get(0).getProduct().getName();

        if (orderItems.size() > 1) {
            name += " 외 %d건".formatted(orderItems.size() - 1);
        }

        return name;
    }

    public String getCode() {
        // yyyy-MM-dd 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime 객체를 문자열로 변환
        return getCreateDate().format(formatter) +  (AppConfig.isNotProd() ? "-test-" + UUID.randomUUID().toString() : "") + "__" + getId();
    }
}