package org.prize.healthapp.application.service

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.port.`in`.TestResultCommand
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestResultQuery
import org.prize.healthapp.domain.testresult.MeasurementData
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.stereotype.Service

@Service
class TestResultService(
    private val testResultQuery: TestResultQuery,
    private val s3Query: S3Query,
) : TestResultCommand {
    override fun createTests(fileInfoDto: FileInfoDto): Int {
        val (fileName) = fileInfoDto
        val csvData: List<Map<String, String>> = s3Query.getCSV(fileName)
        val tests = TestResult.from(csvData)
        testResultQuery.save(tests)
        return tests.size
    }

    override fun getTestAverage(): List<TestResultAvgDto> {
        val tests: List<TestResult> = testResultQuery.findAll()
        val countPerAges = mutableMapOf<Int, Int>()
        val map: MutableMap<AgeSexDto, TestResultAvgDto> = mutableMapOf()
        tests.map { it ->
            val ageSexDto = AgeSexDto(it.age, it.sex)
            if (it.age in 10..19) {
                countPerAges[10] = countPerAges.getOrDefault(10, 0) + 1
            } else if (it.age in 20..29) {
                countPerAges[20] = countPerAges.getOrDefault(20, 0) + 1
            } else if (it.age in 30..39) {
                countPerAges[30] = countPerAges.getOrDefault(30, 0) + 1
            } else if (it.age in 40..49) {
                countPerAges[40] = countPerAges.getOrDefault(40, 0) + 1
            } else if (it.age in 50..59) {
                countPerAges[50] = countPerAges.getOrDefault(50, 0) + 1
            } else if (it.age in 60..69) {
                countPerAges[60] = countPerAges.getOrDefault(60, 0) + 1
            } else if (it.age in 70..79) {
                countPerAges[70] = countPerAges.getOrDefault(70, 0) + 1
            } else if (it.age in 80..89) {
                countPerAges[80] = countPerAges.getOrDefault(80, 0) + 1
            } else if (it.age in 90..150) {
                countPerAges[90] = countPerAges.getOrDefault(90, 0) + 1
            }
            val testResult: TestResultAvgDto = TestResultAvgDto.of(ageSexDto, it.data)
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
            println(key.ages)
            val count = countPerAges[key.ages]!!
            value.divide(count)
        }
        return map.values.toList()
    }

    override fun testMy(myTestResultRequestDto: MyTestResultRequestDto): MyTestResultResponseDto? {
        val tests: List<TestResult> =
            testResultQuery.findByAgeAndSex(myTestResultRequestDto.age, myTestResultRequestDto.sex)
        val data = tests.map { it.data }
        return calculateMyTestResult(myTestResultRequestDto, data)
    }

    private fun calculateMyTestResult(
        dto: MyTestResultRequestDto,
        data: List<MeasurementData>,
    ): MyTestResultResponseDto {
        val heightSum = data.map { it.height }.sum()
        val weightSum = data.map { it.weight }.sum()
        val bmiSum = data.map { it.bmiKgPerM2 }.sum()
        val bodyFatPercentageSum = data.map { it.bodyFatPercentage }.sum()
        val sitUpCountSum = data.map { it.sitUpCount }.sum()
        val crossedSitUpCountSum = data.map { it.crossedSitUpCount }.sum()
        val shuttleRunCountSum = data.map { it.shuttleRunCount }.sum()
        val tenMx4ShuttleRunSecondsSum = data.map { it.tenMx4ShuttleRunSeconds }.sum()
        val standingLongJumpCmSum = data.map { it.standingLongJumpCm }.sum()
        val sitToStandCountSum = data.map { it.sitToStandCount }.sum()
        val twoMinuteSteppingInPlaceCountSum = data.map { it.twoMinuteSteppingInPlaceCount }.sum()
        val treadmill9MinutesBpmSum = data.map { it.treadmill9MinutesBpm }.sum()
        val eightWalk = data.map { it.eightWalk }.sum()
        val reaction = data.map { it.reaction }.sum()
        val grip = data.map { it.grip }.sum()
        return MyTestResultResponseDto(
            age = dto.age,
            sex = dto.sex,
            height = getPercentage(dto.height, heightSum),
            weight = getPercentage(dto.weight, weightSum),
            bodyFatPercentage = getPercentage(dto.bodyFatPercentage, bodyFatPercentageSum),
            sitUpCount = getPercentage(dto.sitUpCount, sitUpCountSum),
            bmiKgPerM2 = getPercentage(dto.bmiKgPerM2, bmiSum),
            crossedSitUpCount = getPercentage(dto.crossedSitUpCount, crossedSitUpCountSum),
            shuttleRunCount = getPercentage(dto.shuttleRunCount, shuttleRunCountSum),
            tenMx4ShuttleRunSeconds = getPercentage(dto.tenMx4ShuttleRunSeconds, tenMx4ShuttleRunSecondsSum),
            standingLongJumpCm = getPercentage(dto.standingLongJumpCm, standingLongJumpCmSum),
            sitToStandCount = getPercentage(dto.sitToStandCount, sitToStandCountSum),
            twoMinuteSteppingInPlaceCount =
                getPercentage(
                    dto.twoMinuteSteppingInPlaceCount,
                    twoMinuteSteppingInPlaceCountSum,
                ),
            treadmill9MinutesBpm = getPercentage(dto.treadmill9MinutesBpm, treadmill9MinutesBpmSum),
            eightWalk = getPercentage(dto.eightWalk, eightWalk),
            reaction = getPercentage(dto.reaction, reaction),
            grip = getPercentage(dto.grip, grip),
        )
    }

    private fun getPercentage(
        myscore: Double?,
        sum: Double,
    ): Double? {
        if (myscore != null) {
            return (myscore / sum * 100)
        }
        return null
    }
}
