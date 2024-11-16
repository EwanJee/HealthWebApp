package org.prize.healthapp.application.service

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.port.`in`.TestResultCommand
import org.prize.healthapp.application.port.out.MemberQuery
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestResultQuery
import org.prize.healthapp.domain.base64.Id
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.member.Member
import org.prize.healthapp.domain.testresult.MeasurementData
import org.prize.healthapp.domain.testresult.TestResult
import org.springframework.stereotype.Service

@Service
class TestResultService(
    private val testResultQuery: TestResultQuery,
    private val s3Query: S3Query,
    private val memberQuery: MemberQuery,
) : TestResultCommand {
    override fun createTests(fileInfoDto: FileInfoDto): Int {
        val (fileName) = fileInfoDto
        val csvData: List<Map<String, String>> = s3Query.getCSV(fileName)
        val tests: List<TestResult> = TestResult.from(csvData)
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
                var gender = "남"
                if (it.sex == "F") {
                    gender = "여"
                }
                AgeSexDto(ageGroup, gender)
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
        val member = calculateMyTestResult(myTestResultRequestDto, data)
        val uuid = memberQuery.save(member)
        return MyTestResultResponseDto(myTestResultRequestDto.name, Id.uuidToBase64(uuid))
    }

    override fun findMyTest(id: String): Member {
        val uuid = Id.base64ToUuid(id)
        val member = memberQuery.findById(uuid)
        return member
    }

    private fun calculateMyTestResult(
        dto: MyTestResultRequestDto,
        data: List<MeasurementData>,
    ): Member {
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
        val thighCircumferenceLeftCmSum = data.map { it.eightWalk }.sum()
        val thighCircumferenceRightCmSum = data.map { it.reaction }.sum()
        val fiveMx4ShuttleRunSecondsSum = data.map { it.grip }.sum()
        val jsonObject =
            buildJsonObject {
                put("height", getPercentage(dto.height, heightSum))
                put("weight", getPercentage(dto.weight, weightSum))
                put("bodyFatPercentage", getPercentage(dto.bodyFatPercentage, bodyFatPercentageSum))
                put("sitUpCount", getPercentage(dto.sitUpCount, sitUpCountSum))
                put("bmiKgPerM2", getPercentage(dto.bmiKgPerM2, bmiSum))
                put("crossedSitUpCount", getPercentage(dto.crossedSitUpCount, crossedSitUpCountSum))
                put("shuttleRunCount", getPercentage(dto.shuttleRunCount, shuttleRunCountSum))
                put(
                    "tenMx4ShuttleRunSeconds",
                    getPercentage(dto.tenMx4ShuttleRunSeconds, tenMx4ShuttleRunSecondsSum),
                )
                put("standingLongJumpCm", getPercentage(dto.standingLongJumpCm, standingLongJumpCmSum))
                put("sitToStandCount", getPercentage(dto.sitToStandCount, sitToStandCountSum))
                put(
                    "twoMinuteSteppingInPlaceCount",
                    getPercentage(dto.twoMinuteSteppingInPlaceCount, twoMinuteSteppingInPlaceCountSum),
                )
                put("treadmill9MinutesBpm", getPercentage(dto.treadmill9MinutesBpm, treadmill9MinutesBpmSum))
                put("eightWalk", getPercentage(dto.eightWalk, thighCircumferenceLeftCmSum))
                put("reaction", getPercentage(dto.reaction, thighCircumferenceRightCmSum))
                put("grip", getPercentage(dto.grip, fiveMx4ShuttleRunSecondsSum))
            }
        return Member(
            name = dto.name,
            age = dto.age,
            sex = dto.sex,
            data = jsonObject,
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
