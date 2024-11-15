@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/health-check")
class HealthCheckController {
    @GetMapping("")
    fun healthCheck(): ResponseEntity<String> = ResponseEntity.ok().body("Health Check")
}
