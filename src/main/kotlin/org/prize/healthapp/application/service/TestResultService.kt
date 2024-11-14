package org.prize.healthapp.application.service

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.port.`in`.TestResultCommand
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestResultQuery
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.testresult.MeasurementData
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.stereotype.Service
import java.util.*

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

        // 테스트 데이터를 나이대와 성별로 그룹화
        val groupedData =
            tests.groupBy {
                val ageGroup =
                    when (it.age) {
                        in 10..19 -> 10
                        in 20..29 -> 20
                        in 30..39 -> 30
                        in 40..49 -> 40
                        in 50..59 -> 50
                        in 60..69 -> 60
                        in 70..79 -> 70
                        in 80..89 -> 80
                        in 90..150 -> 90
                        else -> throw BusinessException(ErrorCode.FILE_NOT_FOUND)
                    }
                AgeSexDto(ageGroup, it.sex)
            }

        // 그룹화된 데이터로 평균 계산
        val result =
            groupedData.map { (ageSexDto, group) ->
                val aggregated =
                    group.fold(TestResultAvgDto.of(ageSexDto, group.first().data)) { acc, testResult ->
                        acc.addData(TestResultAvgDto.of(ageSexDto, testResult.data))
                    }
                aggregated.divide() // 평균 계산
                aggregated
            }

        return result
    }

    override fun testMy(myTestResultRequestDto: MyTestResultRequestDto): MyTestResultResponseDto {
        val tests: List<TestResult> =
            testResultQuery.findByAgeAndSex(myTestResultRequestDto.age, myTestResultRequestDto.sex)
        val data = tests.map { it.data }
        val myTestResponseDto = calculateMyTestResult(myTestResultRequestDto, data)
        testResultQuery.save(myTestResponseDto)
        return myTestResponseDto
    }

    override fun getMy(id: UUID): MyTestResultResponseDto = testResultQuery.findById(id)

    private fun calculateMyTestResult(
        dto: MyTestResultRequestDto,
        data: List<MeasurementData>,
    ): MyTestResultResponseDto {
        // 각 필드의 합계 계산
        val sums =
            data.fold(MeasurementData()) { acc, measurement ->
                MeasurementData(
                    height = acc.height + measurement.height,
                    weight = acc.weight + measurement.weight,
                    bodyFatPercentage = acc.bodyFatPercentage + measurement.bodyFatPercentage,
                    sitUpCount = acc.sitUpCount + measurement.sitUpCount,
                    bmiKgPerM2 = acc.bmiKgPerM2 + measurement.bmiKgPerM2,
                    crossedSitUpCount = acc.crossedSitUpCount + measurement.crossedSitUpCount,
                    shuttleRunCount = acc.shuttleRunCount + measurement.shuttleRunCount,
                    tenMx4ShuttleRunSeconds = acc.tenMx4ShuttleRunSeconds + measurement.tenMx4ShuttleRunSeconds,
                    standingLongJumpCm = acc.standingLongJumpCm + measurement.standingLongJumpCm,
                    sitToStandCount = acc.sitToStandCount + measurement.sitToStandCount,
                    twoMinuteSteppingInPlaceCount = acc.twoMinuteSteppingInPlaceCount + measurement.twoMinuteSteppingInPlaceCount,
                    treadmill9MinutesBpm = acc.treadmill9MinutesBpm + measurement.treadmill9MinutesBpm,
                    eightWalk = acc.eightWalk + measurement.eightWalk,
                    reaction = acc.reaction + measurement.reaction,
                    grip = acc.grip + measurement.grip,
                )
            }

        // 퍼센티지 계산 및 응답 생성
        return MyTestResultResponseDto(
            age = dto.age,
            sex = dto.sex,
            height = getPercentage(dto.height, sums.height),
            weight = getPercentage(dto.weight, sums.weight),
            bodyFatPercentage = getPercentage(dto.bodyFatPercentage, sums.bodyFatPercentage),
            sitUpCount = getPercentage(dto.sitUpCount, sums.sitUpCount),
            bmiKgPerM2 = getPercentage(dto.bmiKgPerM2, sums.bmiKgPerM2),
            crossedSitUpCount = getPercentage(dto.crossedSitUpCount, sums.crossedSitUpCount),
            shuttleRunCount = getPercentage(dto.shuttleRunCount, sums.shuttleRunCount),
            tenMx4ShuttleRunSeconds = getPercentage(dto.tenMx4ShuttleRunSeconds, sums.tenMx4ShuttleRunSeconds),
            standingLongJumpCm = getPercentage(dto.standingLongJumpCm, sums.standingLongJumpCm),
            sitToStandCount = getPercentage(dto.sitToStandCount, sums.sitToStandCount),
            twoMinuteSteppingInPlaceCount = getPercentage(dto.twoMinuteSteppingInPlaceCount, sums.twoMinuteSteppingInPlaceCount),
            treadmill9MinutesBpm = getPercentage(dto.treadmill9MinutesBpm, sums.treadmill9MinutesBpm),
            eightWalk = getPercentage(dto.eightWalk, sums.eightWalk),
            reaction = getPercentage(dto.reaction, sums.reaction),
            grip = getPercentage(dto.grip, sums.grip),
        )
    }

    private fun getPercentage(
        myscore: Double?,
        sum: Double,
    ): Double? = if (myscore != null && sum != 0.0) (myscore / sum * 100) else null
}
