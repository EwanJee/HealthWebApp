package org.prize.healthapp.application.service

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.application.port.`in`.TestCommand
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestQuery
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testQuery: TestQuery,
    private val s3Query: S3Query,
) : TestCommand {
    override fun createTests(fileInfoDto: FileInfoDto): Int {
        val (fileName) = fileInfoDto
        val csvData: List<Map<String, String>> = s3Query.getCSV(fileName)
        val tests = TestResult.from(csvData)
        testQuery.save(tests)
        return tests.size
    }

    override fun getTestAverage(): List<TestResultAvgDto> {
        val tests: List<TestResult> = testQuery.findAll()
        val countPerAges = mutableMapOf<Int, Int>()
        val map: MutableMap<AgeSexDto, TestResultAvgDto> = mutableMapOf()
        tests.map { it ->
            var ageSexDto: AgeSexDto? = null
            if (it.age in 10..19) {
                ageSexDto = AgeSexDto(10, it.sex)
                countPerAges[10] = countPerAges.getOrDefault(10, 0) + 1
            } else if (it.age in 20..29) {
                ageSexDto = AgeSexDto(20, it.sex)
                countPerAges[20] = countPerAges.getOrDefault(20, 0) + 1
            } else if (it.age in 30..39) {
                ageSexDto = AgeSexDto(30, it.sex)
                countPerAges[30] = countPerAges.getOrDefault(30, 0) + 1
            } else if (it.age in 40..49) {
                ageSexDto = AgeSexDto(40, it.sex)
                countPerAges[40] = countPerAges.getOrDefault(40, 0) + 1
            } else if (it.age in 50..59) {
                ageSexDto = AgeSexDto(50, it.sex)
                countPerAges[50] = countPerAges.getOrDefault(50, 0) + 1
            } else if (it.age in 60..69) {
                ageSexDto = AgeSexDto(60, it.sex)
                countPerAges[60] = countPerAges.getOrDefault(60, 0) + 1
            } else if (it.age in 70..79) {
                ageSexDto = AgeSexDto(70, it.sex)
                countPerAges[70] = countPerAges.getOrDefault(70, 0) + 1
            } else if (it.age in 80..89) {
                ageSexDto = AgeSexDto(80, it.sex)
                countPerAges[80] = countPerAges.getOrDefault(80, 0) + 1
            } else if (it.age in 90..99) {
                ageSexDto = AgeSexDto(90, it.sex)
                countPerAges[90] = countPerAges.getOrDefault(90, 0) + 1
            }
            val testResult: TestResultAvgDto = TestResultAvgDto.of(ageSexDto!!, it.data)
            if (map.containsKey(ageSexDto)) {
                val data: TestResultAvgDto = map[ageSexDto]!!
                val newValue = testResult.addData(data)
                map[ageSexDto] = newValue
            } else {
                map[ageSexDto] = testResult
            }
        }
        map.map {
            val (key, value) = it
            val count = countPerAges[key.ages]!!
            value.divide(count)
        }
        return map.values.toList()
    }
}
