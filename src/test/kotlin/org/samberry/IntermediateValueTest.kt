package org.samberry

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.samberry.intermediatevalue.FIRST_VALUE
import org.samberry.intermediatevalue.IntermediateValueService
import org.samberry.intermediatevalue.SECOND_VALUE
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class IntermediateValueTest {
    private lateinit var intermediateValueService: IntermediateValueService
    private lateinit var executorService: ExecutorService

    @Before
    fun setUp() {
        intermediateValueService = IntermediateValueService()
        executorService = Executors.newFixedThreadPool(2)
    }

    @Test
    fun `does not return an intermediate value for concurrent executions using method state`() {
        val results = executorService.invokeAll(listOf(
            Callable {
                intermediateValueService.`fetch value using method state`()
            },
            Callable {
                Thread.sleep(1000) // make sure this execution runs second
                intermediateValueService.`fetch value using method state`()
            }
        )).map { it.get() }

        assertThat(results).containsExactlyInAnyOrder(FIRST_VALUE, SECOND_VALUE)
    }

    /**
     * This exposes a concurrency bug. The first execution returns the value set by the second execution.
     */
    @Test
    fun `does not return an intermediate value for concurrent executions using class state`() {
        val results = executorService.invokeAll(listOf(
            Callable {
                intermediateValueService.`fetch value using class state`()
            },
            Callable {
                Thread.sleep(1000) // make sure this execution runs second
                intermediateValueService.`fetch value using class state`()
            }
        )).map { it.get() }

        assertThat(results).containsExactlyInAnyOrder(FIRST_VALUE, SECOND_VALUE)
    }

    @Test
    fun `does not return an intermediate value for concurrent executions using synchronized class state`() {
        val results = executorService.invokeAll(listOf(
            Callable {
                intermediateValueService.`fetch value using synchronized class methods`()
            },
            Callable {
                Thread.sleep(1000) // make sure this execution runs second
                intermediateValueService.`fetch value using synchronized class methods`()
            }
        )).map { it.get() }

        assertThat(results).containsExactlyInAnyOrder(FIRST_VALUE, SECOND_VALUE)
    }
}