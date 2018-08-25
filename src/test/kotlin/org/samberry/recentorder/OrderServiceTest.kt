package org.samberry.recentorder

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class OrderServiceTest {
    private lateinit var orderService: OrderService

    private lateinit var orderTimeline: OrderTimeline

    @Before
    fun setUp() {
        orderTimeline = mock(OrderTimeline::class.java)

        orderService = OrderService(
            orderTimeline = orderTimeline
        )
    }

    @Test
    fun `adds a order`() {
        val order = Order(
            amount = OrderAmount.ZERO,
            timestamp = OrderTimestamp.now()
        )

        orderService.addOrder(order)

        Mockito.verify(orderTimeline).addOrder(order)
    }
}