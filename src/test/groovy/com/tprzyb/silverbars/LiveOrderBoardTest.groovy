package com.tprzyb.silverbars

import com.tprzyb.silverbars.domain.Order
import com.tprzyb.silverbars.domain.OrderSummary
import spock.lang.Specification
import spock.lang.Subject

import static com.tprzyb.silverbars.domain.Order.Type.BUY
import static com.tprzyb.silverbars.domain.Order.Type.SELL

class LiveOrderBoardTest extends Specification {

    final Order buyOrder1 = new Order('user1', 1.1, 100.10, BUY)
    final Order buyOrder2 = new Order('user2', 2.2, 200.20, BUY)
    final Order sellOrder1 = new Order('user3', 3.3, 300.30, SELL)
    final Order sellOrder2 = new Order('user4', 4.4, 400.40, SELL)

    @Subject
    final LiveOrderBoard liveBoard = new LiveOrderBoard()

    def "provides order summary"() {
        expect:
            liveBoard.getOrdersSummary() instanceof OrderSummary
    }

    def "registers orders"() {
        when:
            liveBoard.register(buyOrder1)
            liveBoard.register(sellOrder1)
            liveBoard.register(buyOrder2)

        then:
            liveBoard.getOrdersSummary() == OrderSummary.of(buyOrder1, buyOrder2, sellOrder1)
    }

    def "allows duplicate orders"() {
        when:
            2.times {
                liveBoard.register(buyOrder1)
            }

        then:
            liveBoard.getOrdersSummary() == OrderSummary.of(buyOrder1, buyOrder1)
    }

    def "fails upon attempt of registering null order"() {
        when:
            liveBoard.register(null)

        then:
            thrown NullPointerException
    }

    def "cancels orders"() {
        given:
            [buyOrder1, buyOrder2, sellOrder1, sellOrder2].each(liveBoard.&register)

        when:
            liveBoard.cancel(sellOrder1)
            liveBoard.cancel(buyOrder1)

        then:
            liveBoard.getOrdersSummary() == OrderSummary.of(buyOrder2, sellOrder2)
    }

    def "fails upon attempt of cancelling non-existent order"() {
        when:
            liveBoard.cancel(sellOrder1)

        then:
            thrown IllegalArgumentException
    }
}