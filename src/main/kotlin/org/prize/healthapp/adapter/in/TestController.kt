@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.TestCommand
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test 컨트롤러", description = "Test 컨트롤러 API")
@RequestMapping("/v1/tests")
@RestController
class TestController(
    val testCommand: TestCommand,
) {
    @Operation(summary = "Test 리스트 생성", description = "csv 파일 url로부터 Test을 생성합니다.")
    @PostMapping("")
    fun createTests(
        @RequestBody fileInfoDto: FileInfoDto,
    ): ResponseEntity<List<TestResult>> {
        val result = testCommand.createTests(fileInfoDto)
        return ResponseEntity.ok().body(result)
    }
}
