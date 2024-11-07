@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.PersonCommand
import org.prize.healthapp.application.service.PersonDistributionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Person 컨트롤러", description = "Person 컨트롤러 API")
@RequestMapping("api/v1/persons")
@RestController
class PersonController(
    private val personCommand: PersonCommand,
) {
    @Operation(summary = "체력 측정 현황 데이터 정리하기 결과값은 데이터 삽입 횟수", description = "csv 파일 url로부터 Person을 생성합니다.")
    @PostMapping("")
    fun createPersons(
        @RequestBody fileInfoDto: FileInfoDto,
    ): ResponseEntity<Int> {
        val result = personCommand.createPersons(fileInfoDto)
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "Person 분포도", description = "분포도를 통해 지역별 체육진흥공단에 관심이 많은 사람 수를 확인합니다.")
    @GetMapping("")
    fun getPersonDistribution(): ResponseEntity<List<PersonDistributionDto>> {
        val result = personCommand.getPersonDistribution()
        return ResponseEntity.ok().body(result)
    }
}
