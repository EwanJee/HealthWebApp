package org.prize.healthapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class HealthappApplication

fun main(args: Array<String>) {
    runApplication<HealthappApplication>(*args)
}
