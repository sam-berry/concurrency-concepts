package org.samberry.recentorder

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.samberry.intermediatevalue.FIRST_REQUEST_VALUE
import org.samberry.intermediatevalue.SECOND_REQUEST_VALUE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntermediateValueResourceTest {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    private lateinit var executorService: ExecutorService

    @Before
    fun setUp() {
        executorService = Executors.newFixedThreadPool(2)
    }

    @After
    fun tearDown() {
        testRestTemplate.exchange(url("request-count"), HttpMethod.DELETE, null, Unit::class.java)
    }

    private fun url(path: String): String {
        return "/intermediate-values/$path"
    }

    @Test
    fun `does not return an intermediate value for concurrent requests using method state`() {
        val results = executorService.invokeAll(listOf(
            Callable {
                testRestTemplate.getForEntity(
                    url("method-state"),
                    String::class.java
                ).body!!
            },
            Callable {
                Thread.sleep(1000) // make sure this request sends second
                testRestTemplate.getForEntity(
                    url("/method-state"),
                    String::class.java
                ).body!!
            }
        )).map { it.get() }

        assertThat(results).containsExactlyInAnyOrder(FIRST_REQUEST_VALUE, SECOND_REQUEST_VALUE)
    }

    /**
     * This exposes a concurrency bug. The first request returns the value set by the second request.
     */
    @Test
    fun `does not return an intermediate value for concurrent requests using class state`() {
        val results = executorService.invokeAll(listOf(
            Callable {
                testRestTemplate.getForEntity(
                    url("/class-state"),
                    String::class.java
                ).body!!
            },
            Callable {
                Thread.sleep(1000) // make sure this request sends second
                testRestTemplate.getForEntity(
                    url("/class-state"),
                    String::class.java
                ).body!!
            }
        )).map { it.get() }

        assertThat(results).containsExactlyInAnyOrder(FIRST_REQUEST_VALUE, SECOND_REQUEST_VALUE)
    }
}