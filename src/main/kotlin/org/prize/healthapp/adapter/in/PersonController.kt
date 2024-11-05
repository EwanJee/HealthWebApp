package org.prize.healthapp.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.prize.healthapp.application.port.`in`.PersonCommand
import org.prize.healthapp.domain.person.Person
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Person 컨트롤러", description = "Person 컨트롤러 API")
@RequestMapping("/v1/person")
@RestController
class PersonController(
    private val personCommand: PersonCommand,
) {
    @Operation(summary = "Person 리스트 생성", description = "csv 파일 url로부터 Person을 생성합니다.")
    @PostMapping("")
    fun createPerson(
        @RequestBody fileInfoDto: FileInfoDto,
    ): ResponseEntity<List<Person>> {
        val result = personCommand.createPersons(fileInfoDto)
        return ResponseEntity.ok().body(result)
    }
}
