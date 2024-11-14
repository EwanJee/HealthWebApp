@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.application.port.out

import org.prize.healthapp.application.service.MyTestResultResponseDto
import org.prize.healthapp.domain.testresult.TestResult
import java.util.*

interface TestResultQuery {
    fun save(tests: List<TestResult>)

    fun findAll(): List<TestResult>

    fun findByAgeAndSex(
        age: Int,
        sex: String,
    ): List<TestResult>

    fun save(test: MyTestResultResponseDto)

    fun findById(id: UUID): MyTestResultResponseDto
}
