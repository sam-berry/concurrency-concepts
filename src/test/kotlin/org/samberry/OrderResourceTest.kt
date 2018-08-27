package org.samberry

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.samberry.recentorder.EMPTY_STATISTICS
import org.samberry.recentorder.Order
import org.samberry.recentorder.OrderAmount
import org.samberry.recentorder.OrderStatistics
import org.samberry.recentorder.OrderTimestamp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderResourceTest {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @After
    fun tearDown() {
        testRestTemplate.exchange("/orders", HttpMethod.DELETE, null, Unit::class.java)
    }

    @Test
    fun `returns 201 on successful create`() {
        val order = Order(
            amount = OrderAmount(10.0),
            timestamp = OrderTimestamp.now()
        )

        val result = testRestTemplate.postForEntity("/orders", order, Unit.javaClass)

        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun `can delete orders`() {
        val order = Order(
            amount = OrderAmount(10.0),
            timestamp = OrderTimestamp.now()
        )

        testRestTemplate.postForEntity(
            "/orders", order, Any::class.java
        )
        testRestTemplate.postForEntity(
            "/orders", order, Any::class.java
        )

        var stats = testRestTemplate.getForEntity(
            "/statistics", OrderStatistics::class.java
        )
        assertThat(stats.body).isNotNull
        assertThat(stats.body!!.sum).isEqualTo(OrderAmount(20))

        testRestTemplate.delete("/orders")

        stats = testRestTemplate.getForEntity(
            "/statistics", OrderStatistics::class.java
        )
        assertThat(stats.body).isNotNull
        assertThat(stats.body!!).isEqualTo(EMPTY_STATISTICS)
    }
}