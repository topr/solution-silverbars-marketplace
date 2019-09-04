package com.tprzyb.silverbars;

import com.tprzyb.silverbars.domain.Order;
import com.tprzyb.silverbars.domain.OrderSummary;

import java.util.LinkedList;
import java.util.List;

import static com.tprzyb.silverbars.common.Validation.checkIsTrue;
import static com.tprzyb.silverbars.domain.OrderSummary.summarise;
import static java.util.Objects.requireNonNull;

public class LiveOrderBoard {

    private final List<Order> orders = new LinkedList<>();

    public OrderSummary getOrdersSummary() {
        return summarise(orders);
    }

    public void register(Order order) {
        orders.add(requireNonNull(order));
    }

    public void cancel(Order order) {
        checkIsTrue(orders.remove(order));
    }
}
