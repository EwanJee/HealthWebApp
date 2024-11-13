@file:Suppress("ktlint:standard:no-wildcard-imports", "ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.adapter.out.s3.FileUploadResponseDto
import org.prize.healthapp.application.port.`in`.FileCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "File 컨트롤러", description = "File 컨트롤러 API")
@RequestMapping("/api/v1/file")
@RestController
class FileController(
    private val fileCommand: FileCommand,
) {
    @Operation(summary = "Person 데이터 삽입 결과 값은 데이터 들어간 횟수", description = "csv 파일 url로부터 Person을 생성합니다.")
    @PostMapping("/persons")
    fun uploadPersons(
        @RequestPart("file") multiPartFile: MultipartFile,
    ): ResponseEntity<FileUploadResponseDto> {
        val result = fileCommand.uploadPersons(multiPartFile)
        return ResponseEntity.ok().body(result)
    }

    @Operation(summary = "Test 데이터 삽입 결과 값은 데이터 들어간 횟수", description = "csv 파일 url로부터 Test을 생성합니다.")
    @PostMapping("/tests")
    fun uploadTests(
        @RequestPart("file") multiPartFile: MultipartFile,
    ): ResponseEntity<FileUploadResponseDto> {
        val result = fileCommand.uploadTests(multiPartFile)
        return ResponseEntity.ok().body(result)
    }
}
