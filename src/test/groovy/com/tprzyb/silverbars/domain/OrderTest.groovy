package com.tprzyb.silverbars.domain

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification
import spock.lang.Unroll

import static com.tprzyb.silverbars.domain.Order.Type.SELL
import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.ZERO
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable

class OrderTest extends Specification {

    final String userId = 'joe'
    final BigDecimal quantity = 5.0
    final BigDecimal price = 333
    final Order.Type type = SELL

    @Unroll
    def "creates correct instance [#aType]"() {
        given:
            def order = new Order(userId, quantity, price, aType)

        expect:
            order.userId == userId
            order.quantity == quantity
            order.price == price
            order.type == aType

        where:
            aType << Order.Type.values().toList()
    }

    @Unroll
    def "fails on non-blank user id [#blankUserId]"() {
        when:
            new Order(blankUserId, quantity, price, type)

        then:
            thrown expectedException

        where:
            blankUserId || expectedException
            null        || NullPointerException
            ''          || IllegalArgumentException
            '\t'        || IllegalArgumentException
            '   '       || IllegalArgumentException
            '\n'        || IllegalArgumentException
    }

    @Unroll
    def "fails on non-positive quantity [#nonPositiveQuantity]"() {
        when:
            new Order(userId, nonPositiveQuantity, price, type)

        then:
            thrown expectedException

        where:
            nonPositiveQuantity || expectedException
            null                || NullPointerException
            ZERO                || IllegalArgumentException
            -ONE                || IllegalArgumentException
    }

    @Unroll
    def "fails on non-positive price [#nonPositivePrice]"() {
        when:
            new Order(userId, quantity, nonPositivePrice, type)

        then:
            thrown expectedException

        where:
            nonPositivePrice || expectedException
            null             || NullPointerException
            ZERO             || IllegalArgumentException
            -ONE             || IllegalArgumentException
    }

    def "requires type"() {
        given:
            def noType = null

        when:
            new Order(userId, quantity, price, noType)

        then:
            thrown NullPointerException
    }

    def "has correct equals and hashCode contract"() {
        given:
            def verifier = EqualsVerifier.forClass(Order)

        expect:
            verifier.verify()
    }

    def "is immutable"() {
        expect:
            assertImmutable(Order)
    }
}
