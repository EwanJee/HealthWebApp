@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.FileCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "File 컨트롤러", description = "File 컨트롤러 API")
@RequestMapping("/v1/file")
@RestController
class FileController(
    private val fileCommand: FileCommand,
) {
    @PostMapping("/persons")
    fun uploadPersons(
        @RequestParam("file") multiPartFile: MultipartFile,
    ): ResponseEntity<Int> {
        val result = fileCommand.uploadPersons(multiPartFile)
        return ResponseEntity.ok().body(result)
    }

    @PostMapping("/tests")
    fun uploadTests(
        @RequestParam("file") multiPartFile: MultipartFile,
    ): ResponseEntity<Int> {
        val result = fileCommand.uploadTests(multiPartFile)
        return ResponseEntity.ok().body(result)
    }
}
