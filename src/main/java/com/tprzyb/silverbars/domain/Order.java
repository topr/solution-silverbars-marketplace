package com.tprzyb.silverbars.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static com.tprzyb.silverbars.common.Validation.requireGreaterThanZero;
import static com.tprzyb.silverbars.common.Validation.requireNonBlank;
import static java.util.Objects.requireNonNull;

public final class Order {

    private final String userId;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final Type type;

    enum Type { BUY, SELL }

    public Order(String userId, BigDecimal quantity, BigDecimal price, Type type) {
        this.userId = requireNonBlank(userId);
        this.quantity = requireGreaterThanZero(quantity);
        this.price = requireGreaterThanZero(price);
        this.type = requireNonNull(type);
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;

        return Objects.equals(userId, order.userId) &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(price, order.price) &&
                type == order.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quantity, price, type);
    }
}