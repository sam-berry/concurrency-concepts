package org.samberry.uniquethreads

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/unique-threads")
@RestController
class UniqueThreadsResource {
    @ResponseBody
    @GetMapping("/current-thread-id")
    fun fetchThreadId(): Long {
        return Thread.currentThread().id
    }
}