package org.samberry.intermediatevalue

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

const val FIRST_REQUEST_VALUE = "first value"
const val SECOND_REQUEST_VALUE = "second value"

@Service
class IntermediateValueService {
    private var requestCount: AtomicInteger = AtomicInteger(0)
    private var classLevelValue: String? = null

    fun `fetch value using method state`(): String {
        val count = requestCount.get()
        val value: String
        if (count == 0) {
            requestCount.incrementAndGet()
            value = FIRST_REQUEST_VALUE
            Thread.sleep(5000)
        } else {
            requestCount.incrementAndGet()
            value = SECOND_REQUEST_VALUE
        }

        return value
    }

    fun `fetch value using class state`(): String {
        val count = requestCount.get()
        if (count == 0) {
            requestCount.incrementAndGet()
            classLevelValue = FIRST_REQUEST_VALUE
            Thread.sleep(5000)
        } else {
            requestCount.incrementAndGet()
            classLevelValue = SECOND_REQUEST_VALUE
        }

        return classLevelValue!!
    }

    fun deleteRequestCount() {
        requestCount.set(0)
    }
}