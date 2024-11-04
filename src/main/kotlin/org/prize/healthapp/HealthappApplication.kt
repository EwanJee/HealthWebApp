package org.prize.healthapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HealthappApplication

fun main(args: Array<String>) {
    runApplication<HealthappApplication>(*args)
}
