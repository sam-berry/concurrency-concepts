package org.samberry.intermediatevalue

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

const val FIRST_VALUE = "first value"
const val SECOND_VALUE = "second value"

@Service
class IntermediateValueService {
    private var requestCount: AtomicInteger = AtomicInteger(0)
    private var classLevelValue: String? = null
    private var syncdClassLevelValue: String? = null

    fun `fetch value using method state`(): String {
        val count = requestCount.get()
        var value: String? = null
        if (count == 0) {
            requestCount.incrementAndGet()
            value = FIRST_VALUE
            Thread.sleep(5000)
        } else if (count == 1) {
            requestCount.incrementAndGet()
            value = SECOND_VALUE
        }

        return value ?: ""
    }

    fun `fetch value using class state`(): String {
        val count = requestCount.get()
        if (count == 0) {
            requestCount.incrementAndGet()
            classLevelValue = FIRST_VALUE
            Thread.sleep(5000)
        } else if (count == 1) {
            requestCount.incrementAndGet()
            classLevelValue = SECOND_VALUE
        }

        return classLevelValue ?: ""
    }

    @Synchronized
    private fun setValue(value: String) {
        syncdClassLevelValue = value
    }

    @Synchronized
    private fun getValue(): String? {
        return syncdClassLevelValue
    }

    fun `fetch value using synchronized class methods`(): String {
        val count = requestCount.get()
        if (count == 0) {
            requestCount.incrementAndGet()
            setValue(FIRST_VALUE)
            Thread.sleep(5000)
        } else if (count == 1) {
            requestCount.incrementAndGet()
            setValue(SECOND_VALUE)
        }

        return getValue() ?: ""
    }

    fun deleteRequestCount() {
        requestCount.set(0)
    }
}