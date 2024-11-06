@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.TestCommand
import org.prize.healthapp.application.service.TestResultAvgDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test 컨트롤러", description = "Test 컨트롤러 API")
@RequestMapping("/v1/tests")
@RestController
class TestController(
    private val testCommand: TestCommand,
) {
    @Operation(summary = "체력 측정 항목별 측정 데이터 삽입 결과 값은 데이터 들어간 횟수", description = "csv 파일 url로부터 Test을 생성합니다.")
    @PostMapping("")
    fun createTests(
        @RequestBody fileInfoDto: FileInfoDto,
    ): ResponseEntity<Int> {
        val result = testCommand.createTests(fileInfoDto)
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "Test 종합해서 나이 별로 평균내기", description = "나이 별로 평균을 내어 반환합니다.")
    @GetMapping("")
    fun getTestAverage(): ResponseEntity<List<TestResultAvgDto>> {
        val result = testCommand.getTestAverage()
        return ResponseEntity.ok().body(result)
    }
}
