package org.samberry.intermediatevalue

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/intermediate-values")
@RestController
class IntermediateValueResource(
    private val intermediateValueService: IntermediateValueService
) {
    @ResponseBody
    @GetMapping("/method-state")
    fun `fetch value using method state`(): String {
        return intermediateValueService.`fetch value using method state`()
    }

    @ResponseBody
    @GetMapping("/class-state")
    fun `fetch value using class state`(): String {
        return intermediateValueService.`fetch value using class state`()
    }

    @ResponseBody
    @DeleteMapping("/request-count")
    fun deleteRequestCount() {
        intermediateValueService.deleteRequestCount()
    }
}