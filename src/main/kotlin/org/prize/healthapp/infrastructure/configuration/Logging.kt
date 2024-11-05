package org.prize.healthapp.infrastructure.configuration

import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Logging {
    @Bean
    fun logger(): Logger = org.slf4j.LoggerFactory.getLogger("org.prize.healthapp")
}
