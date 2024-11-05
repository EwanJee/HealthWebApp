@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "File 컨트롤러", description = "File 컨트롤러 API")
@RequestMapping("/v1/file")
@RestController
class FileController {
    @PostMapping("")
    fun uploadCSV() {
    }
}
