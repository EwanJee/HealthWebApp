@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.StadiumCommand
import org.prize.healthapp.application.service.StadiumResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "운동장 컨트롤러", description = "운동장 컨트롤러 API")
@RequestMapping("api/v1/stadium")
@RestController
class StadiumController(
    private val stadiumCommand: StadiumCommand,
) {
    @Operation(summary = "운동장 정보 조회")
    @GetMapping("")
    suspend fun getStadiumInfo(
        @RequestParam("city") city: String,
        @RequestParam("district") district: String,
        @RequestParam("page", defaultValue = "0") page: Int, // 기본값 0
        @RequestParam("size", defaultValue = "300") size: Int, // 기본값 10
    ): ResponseEntity<Page<StadiumResponseDto>> {
        val pageable = PageRequest.of(page, size)
        val result = stadiumCommand.getBy(city, district, pageable)
        return ResponseEntity.ok().body(result)
    }
}
