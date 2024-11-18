package org.prize.healthapp.infrastructure.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class ClientConfig(
    @Value("\${api_stadium_key}")
    private val apiKey: String,
) {
    private val url = "http://apis.data.go.kr/B551014/SRVC_API_SFMS_FACI/TODZ_API_SFMS_FACI"

    @Bean
    fun client(objectMapper: ObjectMapper): WebClient =
        WebClient
            .builder()
            .baseUrl(url)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) // 응답 크기 증가 (필요 시)
            }.uriBuilderFactory(
                DefaultUriBuilderFactory("$url?serviceKey=$apiKey")
                    .apply { encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE },
            ).build()
}
