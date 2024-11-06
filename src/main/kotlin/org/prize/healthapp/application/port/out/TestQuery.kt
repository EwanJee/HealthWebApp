package org.prize.healthapp.application.port.out

import org.prize.healthapp.domain.testresult.TestResult

interface TestQuery {
    fun save(tests: List<TestResult>)

    fun findAll(): List<TestResult>
}
