package org.prize.healthapp.application.service

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.application.port.`in`.TestCommand
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestQuery
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.stereotype.Service

@Service
class TestService(
    val testQuery: TestQuery,
    private val s3Query: S3Query,
) : TestCommand {
    override fun createTests(fileInfoDto: FileInfoDto): List<TestResult> {
        val (fileName) = fileInfoDto
        val csvData: List<Map<String, String>> = s3Query.getCSV(fileName)
        val tests = TestResult.from(csvData)
        testQuery.save(tests)
        return tests
    }
}
