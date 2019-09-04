package com.tprzyb.silverbars.domain

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable

class OrderSummaryPositionTest extends Specification {

    final BigDecimal quantity = 3.5
    final BigDecimal price = 299

    def "creates correct instance"() {
        given:
            def summaryPosition = new OrderSummaryPosition(quantity, price)

        expect:
            summaryPosition.quantity == quantity
            summaryPosition.price == price
    }

    def "requires quantity"() {
        given:
            def noQuantity = null

        when:
            new OrderSummaryPosition(noQuantity, price)

        then:
            thrown NullPointerException
    }

    def "requires price"() {
        given:
            def noPrice = null

        when:
            new OrderSummaryPosition(quantity, noPrice)

        then:
            thrown NullPointerException
    }

    def "has correct equals and hashCode contract"() {
        given:
            def verifier = EqualsVerifier.forClass(OrderSummaryPosition)

        expect:
            verifier.verify()
    }

    def "is immutable"() {
        expect:
            assertImmutable(OrderSummaryPosition)
    }
}
