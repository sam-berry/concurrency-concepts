package org.samberry.uniquethreads

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap

@RequestMapping("/unique-threads")
@RestController
class UniqueThreadsResource {
    private var threads = ConcurrentHashMap<Long, String>()

    @ResponseBody
    @GetMapping("/current-thread-id")
    fun fetchThreadId(): Long {
        val threadId = Thread.currentThread().id
        threads[threadId] = ""
        return threadId
    }

    @ResponseBody
    @GetMapping
    fun fetchUniqueThreads(): Set<Long> {
        return threads.keys().toList().toSet()
    }

    @DeleteMapping
    fun deleteUniqueThreads() {
        threads.clear()
    }
}