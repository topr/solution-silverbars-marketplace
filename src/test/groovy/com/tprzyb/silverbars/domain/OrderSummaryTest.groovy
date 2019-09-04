package com.tprzyb.silverbars.domain

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.tprzyb.silverbars.domain.Order.Type.BUY
import static com.tprzyb.silverbars.domain.Order.Type.SELL
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable

class OrderSummaryTest extends Specification {

    def "can be empty"() {
        when:
            OrderSummary.of()

        then:
            noExceptionThrown()
    }

    def "groups orders by type"() {
        given:
            def summary = OrderSummary.of(
                    order('any-user', 10, 100, BUY),
                    order('any-user', 20, 200, SELL)
            )

        expect:
            summary.getBuyPositions() == [
                    position(10, 100)
            ]

        and:
            summary.getSellPositions() == [
                    position(20, 200)
            ]
    }

    def "sorts buy orders descending by price"() {
        given:
            def summary = OrderSummary.of(
                    order('any-user', 1, 305, BUY),
                    order('any-user', 2, 310, BUY),
                    order('any-user', 3, 300, BUY)
            )

        expect:
            summary.getBuyPositions() == [
                    position(2, 310),
                    position(1, 305),
                    position(3, 300)
            ]
    }

    def "sorts sell orders ascending by price"() {
        given:
            def summary = OrderSummary.of(
                    order('any-user', 1, 305, SELL),
                    order('any-user', 2, 310, SELL),
                    order('any-user', 3, 300, SELL)
            )

        expect:
            summary.getSellPositions() == [
                    position(3, 300),
                    position(1, 305),
                    position(2, 310)
            ]
    }

    def "merges orders of the same price and type even from different users"() {
        given:
            def summary = OrderSummary.of(
                    order('user1', 3.3, 299.99, BUY),
                    order('user2', 4.0, 303.33, SELL),
                    order('user3', 2.2, 299.99, BUY),
                    order('user4', 6.0, 303.33, SELL)
            )

        expect:
            summary.getBuyPositions() == [
                    position(5.5, 299.99)
            ]

        and:
            summary.getSellPositions() == [
                    position(10.0, 303.33)
            ]
    }

    def "has correct equals and hashCode contract"() {
        given:
            def verifier = EqualsVerifier.forClass(OrderSummary)

        expect:
            verifier.verify()
    }

    def "is immutable"() {
        expect:
            assertImmutable(OrderSummary)
    }

    private static Order order(String userId, BigDecimal quantity, BigDecimal price, Order.Type type) {
        new Order(userId, quantity, price, type)
    }

    private static Order order(String userId, int quantity, int price, Order.Type type) {
        order(userId, quantity as BigDecimal, price as BigDecimal, type)
    }

    private static OrderSummaryPosition position(BigDecimal quantity, BigDecimal price) {
        new OrderSummaryPosition(quantity, price)
    }

    private static OrderSummaryPosition position(int quantity, int price) {
        position(quantity as BigDecimal, price as BigDecimal)
    }
}
