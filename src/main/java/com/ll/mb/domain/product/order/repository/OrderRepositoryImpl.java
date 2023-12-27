package com.ll.mb.domain.product.order.repository;

import com.ll.mb.domain.member.member.entity.Member;
import com.ll.mb.domain.product.order.entity.Order;
import com.ll.mb.standard.util.Ut;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import static com.ll.mb.domain.product.order.entity.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Order> search(Member buyer, Boolean payStatus, Boolean cancelStatus, Boolean refundStatus, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (buyer != null) {
            builder.and(order.buyer.eq(buyer));
        }

        if (Ut.match.isTrue(payStatus)) {
            builder.and(order.payDate.isNotNull());
        }

        if (Ut.match.isFalse(payStatus)) {
            builder.and(order.payDate.isNull());
        }

        if (Ut.match.isTrue(cancelStatus)) {
            builder.and(order.cancelDate.isNotNull());
        }

        if (Ut.match.isFalse(cancelStatus)) {
            builder.and(order.cancelDate.isNull());
        }

        if (Ut.match.isTrue(refundStatus)) {
            builder.and(order.refundDate.isNotNull());
        }

        if (Ut.match.isFalse(refundStatus)) {
            builder.and(order.refundDate.isNull());
        }

        JPAQuery<Order> ordersQuery = jpaQueryFactory
                .select(order)
                .from(order)
                .where(builder);

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(order.getType(), order.getMetadata());
            ordersQuery.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, pathBuilder.get(o.getProperty())));
        }

        ordersQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(order.count())
                .from(order)
                .where(builder);

        return PageableExecutionUtils.getPage(ordersQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}