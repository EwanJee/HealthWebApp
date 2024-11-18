package org.prize.healthapp.adapter.out.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.reactive.awaitSingle
import org.prize.healthapp.application.port.out.ClientQuery
import org.prize.healthapp.application.service.dto.ApiResponse
import org.prize.healthapp.application.service.dto.StadiumItem
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.slf4j.Logger
import org.springframework.web.reactive.function.client.WebClient
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Adapter
class ClientAdapter(
    private val client: WebClient,
    private val logger: Logger,
) : ClientQuery {
    override suspend fun getBy(
        city: String,
        district: String,
    ): List<StadiumItem> {
        val objectMapper = jacksonObjectMapper()

        val encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString())
        val encodedDistrict = URLEncoder.encode(district, StandardCharsets.UTF_8.toString())
        val rawResponse: String =
            client
                .get()
                .uri { uriBuilder ->
                    uriBuilder
                        .queryParam("cp_nm", encodedCity)
                        .queryParam("cpb_nm", encodedDistrict)
                        .queryParam("resultType", "JSON")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 1000)
                        .build()
                }.retrieve()
                .bodyToMono(String::class.java)
                .awaitSingle()
        val apiResponse: ApiResponse = objectMapper.readValue(rawResponse)

        return apiResponse.response.body.items.item
    }
}
