@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.testresult

import org.prize.healthapp.application.port.out.TestQuery
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.testresult.TestResult
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.slf4j.Logger
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = false)
@Adapter
class TestResultAdapter(
    private val testResultRepository: TestResultRepository,
    private val jdbcTemplate: JdbcTemplate,
    private val logger: Logger,
) : TestQuery {
    @Transactional
    override fun save(tests: List<TestResult>) {
        val testEntities: List<TestResultEntity> =
            tests.map { TestResultEntity.from(it) }
        logger.info("start testResult bulk save")
        val query = """INSERT INTO test_result (id, age, sex, data) VALUES (UUID(), ?, ?, ?)"""
        try {
            jdbcTemplate.batchUpdate(query, testEntities, testEntities.size) { ps, testResultEntity ->
                ps.setInt(1, testResultEntity.age)
                ps.setString(2, testResultEntity.sex)
                ps.setString(3, testResultEntity.data)
            }
        } catch (e: Exception) {
            logger.error("testResult bulk save failed", e)
            throw BusinessException(ErrorCode.TOO_MANY_REQUEST)
        }
    }

    override fun findAll(): List<TestResult> = testResultRepository.findAll().map { it.toTestResult() }
}
