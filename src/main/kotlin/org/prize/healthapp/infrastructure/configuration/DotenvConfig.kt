package org.prize.healthapp.infrastructure.configuration

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DotenvConfig {
    @Bean
    fun dotenv(): Dotenv = Dotenv.configure().ignoreIfMissing().load()
}
