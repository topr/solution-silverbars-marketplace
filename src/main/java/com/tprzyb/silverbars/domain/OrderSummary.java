package com.tprzyb.silverbars.domain;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;

import static com.tprzyb.silverbars.domain.Order.Type.BUY;
import static com.tprzyb.silverbars.domain.Order.Type.SELL;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public final class OrderSummary {

    private final List<OrderSummaryPosition> buyPositions;
    private final List<OrderSummaryPosition> sellPositions;

    private OrderSummary(List<OrderSummaryPosition> buyPositions, List<OrderSummaryPosition> sellPositions) {
        this.buyPositions = unmodifiableList(new ArrayList<>(buyPositions));
        this.sellPositions = unmodifiableList(new ArrayList<>(sellPositions));
    }

    public static OrderSummary summarise(List<Order> orders) {
        Map<Order.Type, Collection<OrderSummaryPosition>> summarisedOrders = summariseByTypeAndPrice(orders);

        return new OrderSummary(
                sort(summarisedOrders, BUY, comparingByPrice().reversed()),
                sort(summarisedOrders, SELL, comparingByPrice())
        );
    }

    public static OrderSummary of(Order... orders) {
        return summarise(asList(orders));
    }

    public List<OrderSummaryPosition> getBuyPositions() {
        return buyPositions;
    }

    public List<OrderSummaryPosition> getSellPositions() {
        return sellPositions;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        OrderSummary that = (OrderSummary) other;

        return Objects.equals(buyPositions, that.buyPositions) &&
                Objects.equals(sellPositions, that.sellPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyPositions, sellPositions);
    }

    private static Map<Order.Type, Collection<OrderSummaryPosition>> summariseByTypeAndPrice(List<Order> orders) {
        return orders.stream()
                .collect(
                        groupingBy(
                                Order::getType,
                                mergingByPrice()
                        )
                );
    }

    private static Collector<Order, ?, Collection<OrderSummaryPosition>> mergingByPrice() {
        return collectingAndThen(
                groupingBy(
                        Order::getPrice,
                        collectingAndThen(
                                toList(),
                                OrderSummary::mergeOrders
                        )
                ),
                Map::values
        );
    }

    private static OrderSummaryPosition mergeOrders(List<Order> orders) {
        return new OrderSummaryPosition(combineQuantityOf(orders), orders.get(0).getPrice());
    }

    private static BigDecimal combineQuantityOf(List<Order> orders) {
        return orders.stream()
                .map(Order::getQuantity)
                .reduce(BigDecimal::add)
                .orElseThrow(IllegalArgumentException::new);
    }

    private static List<OrderSummaryPosition> sort(
            Map<Order.Type, Collection<OrderSummaryPosition>> orders,
            Order.Type type,
            Comparator<OrderSummaryPosition> comparator) {
        return orders.getOrDefault(type, emptyList())
                .stream()
                .sorted(comparator)
                .collect(toList());
    }

    private static Comparator<OrderSummaryPosition> comparingByPrice() {
        return comparing(OrderSummaryPosition::getPrice);
    }
}
