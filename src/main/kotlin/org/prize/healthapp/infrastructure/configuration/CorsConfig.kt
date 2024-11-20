package org.prize.healthapp.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer =
        object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins(
                        "https://myhealthcheck.xyz",
                        "https://www.myhealthcheck.xyz",
                        "http://myhealthcheck.xyz",
                        "http://www.myhealthcheck.xyz",
                    ).allowedMethods("GET", "POST")
                    .allowedHeaders("*")
                    .allowCredentials(true)
            }
        }
}
