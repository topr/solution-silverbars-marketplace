package com.tprzyb.silverbars.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static com.tprzyb.silverbars.common.Validation.requireGreaterThanZero;

final class OrderSummaryPosition {

    private final BigDecimal quantity;
    private final BigDecimal price;

    OrderSummaryPosition(BigDecimal quantity, BigDecimal price) {
        this.quantity = requireGreaterThanZero(quantity);
        this.price = requireGreaterThanZero(price);
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSummaryPosition position = (OrderSummaryPosition) o;

        return Objects.equals(quantity, position.quantity) &&
                Objects.equals(price, position.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, price);
    }
}
