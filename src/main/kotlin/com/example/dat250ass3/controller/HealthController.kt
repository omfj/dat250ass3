package com.example.dat250ass3.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health")
@RestController
@RequestMapping("/api/health")
class HealthController {
    @GetMapping
    fun health(): String {
        return "OK"
    }
}
