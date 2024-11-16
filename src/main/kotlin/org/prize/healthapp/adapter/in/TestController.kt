@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.TestResultCommand
import org.prize.healthapp.application.service.MyTestResultResponseDto
import org.prize.healthapp.application.service.TestResultAvgDto
import org.prize.healthapp.domain.member.Member
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test 컨트롤러", description = "Test 컨트롤러 API")
@RequestMapping("/api/v1/tests")
@RestController
class TestController(
    private val testResultCommand: TestResultCommand,
) {
    @Operation(summary = "체력 측정 항목별 측정 데이터 삽입 결과 값은 데이터 들어간 횟수", description = "csv 파일 url로부터 Test을 생성합니다.")
    @PostMapping("")
    fun createTests(
        @RequestBody fileInfoDto: FileInfoDto,
    ): ResponseEntity<Int> {
        val result = testResultCommand.createTests(fileInfoDto)
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "Test 종합해서 나이 별로 평균내기", description = "나이 별로 평균을 내어 반환합니다.")
    @GetMapping("")
    fun getTestAverage(): ResponseEntity<List<TestResultAvgDto>> {
        val result = testResultCommand.getTestAverage()
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "내가 입력한 Test 결과 값", description = "나의 Test 에 대한 id를 반환합니다.")
    @PostMapping("/my")
    fun postMyResult(
        @RequestBody myTestResultRequestDto: MyTestResultRequestDto,
    ): ResponseEntity<MyTestResultResponseDto> {
        val result = testResultCommand.testMy(myTestResultRequestDto)
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "내가 입력한 Test 결과 값", description = "나의 Test 에 대한 정보를 반환합니다.")
    @GetMapping("/my")
    fun getMyResult(
        @RequestParam("id") id: String,
    ): ResponseEntity<Member> {
        val result = testResultCommand.findMyTest(id)
        return ResponseEntity.ok().body(result)
    }
}
