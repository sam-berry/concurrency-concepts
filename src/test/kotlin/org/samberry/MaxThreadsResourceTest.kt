package org.samberry

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MaxThreadsResourceTest {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    private lateinit var executorService: ExecutorService

    @Before
    fun setUp() {
        executorService = Executors.newFixedThreadPool(2)
    }

    private fun url(path: String): String {
        return "/unique-threads/$path"
    }

    @Test
    fun `can provide the expected number of threads`() {
        val expectedNumberOfThreads = 30

        val workers = (0..(expectedNumberOfThreads - 1))
            .map {
                Callable {
                    testRestTemplate.getForEntity(
                        url("current-thread-id"),
                        Long::class.java
                    ).body!!
                }
            }

        val threadIds = executorService.invokeAll(workers)
            .map { it.get() }
            .toSet()

        assertThat(threadIds).hasSize(expectedNumberOfThreads)
    }
}