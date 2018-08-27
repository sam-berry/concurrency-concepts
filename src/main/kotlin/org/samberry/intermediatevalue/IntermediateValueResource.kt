package org.samberry.intermediatevalue

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class IntermediateValueResource(
    private val intermediateValueService: IntermediateValueService
) {
    @ResponseBody
    @GetMapping("/intermediate-values")
    fun fetchValue(): String {
        return intermediateValueService.fetchValue()
    }
}