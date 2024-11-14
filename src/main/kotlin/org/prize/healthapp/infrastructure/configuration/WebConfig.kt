package org.prize.healthapp.infrastructure.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/v1/**") // CORS를 허용할 URL 패턴
            .allowedOrigins("http://localhost:8080") // 허용할 도메인
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
            .allowedHeaders("*") // 허용할 헤더
            .allowCredentials(true) // 쿠키 전송 허용 여부
    }
}
