package org.prize.healthapp.application.service

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.port.`in`.TestResultCommand
import org.prize.healthapp.application.port.out.MemberQuery
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestResultQuery
import org.prize.healthapp.domain.base64.toBase64
import org.prize.healthapp.domain.base64.toUUID
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
            testResultQuery.findByAgeAndSex(
                calculateDecade(myTestResultRequestDto.age),
                findGender(myTestResultRequestDto.sex),
            )
        val data = tests.map { it.data }
        val member = calculateMyTestResult(myTestResultRequestDto, data)
        val uuid = memberQuery.save(member)
        return MyTestResultResponseDto(myTestResultRequestDto.name, uuid.toBase64())
    }

    private fun findGender(sex: String): String =
        if (sex.contains("남")) {
            "M"
        } else {
            "F"
        }

    private fun calculateDecade(age: Int): Int =
        if (age < 0) {
            throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        } else {
            age / 10 * 10
        }

    override fun findMyTest(id: String): Member {
        val uuid = id.toUUID()
        val member = memberQuery.findById(uuid)
        return member
    }

    private fun calculateMyTestResult(
        dto: MyTestResultRequestDto,
        data: List<MeasurementData>,
    ): Member {
        val jsonObject =
            buildJsonObject {
                put("height", getPercentile(dto.height, data.map { it.height }))
                put("weight", getPercentile(dto.weight, data.map { it.weight }))
                put("bodyFatPercentage", getPercentile(dto.bodyFatPercentage, data.map { it.bodyFatPercentage }))
                put("sitUpCount", getPercentile(dto.sitUpCount, data.map { it.sitUpCount }))
                put("bmiKgPerM2", getPercentile(dto.bmiKgPerM2, data.map { it.bmiKgPerM2 }))
                put("crossedSitUpCount", getPercentile(dto.crossedSitUpCount, data.map { it.crossedSitUpCount }))
                put("shuttleRunCount", getPercentile(dto.shuttleRunCount, data.map { it.shuttleRunCount }))
                put(
                    "tenMx4ShuttleRunSeconds",
                    getPercentile(dto.tenMx4ShuttleRunSeconds, data.map { it.tenMx4ShuttleRunSeconds }),
                )
                put("standingLongJumpCm", getPercentile(dto.standingLongJumpCm, data.map { it.standingLongJumpCm }))
                put("sitToStandCount", getPercentile(dto.sitToStandCount, data.map { it.sitToStandCount }))
                put(
                    "twoMinuteSteppingInPlaceCount",
                    getPercentile(dto.twoMinuteSteppingInPlaceCount, data.map { it.twoMinuteSteppingInPlaceCount }),
                )
                put(
                    "treadmill9MinutesBpm",
                    getPercentile(dto.treadmill9MinutesBpm, data.map { it.treadmill9MinutesBpm }),
                )
                put("eightWalk", getPercentile(dto.eightWalk, data.map { it.eightWalk }))
                put("reaction", getPercentile(dto.reaction, data.map { it.reaction }))
                put("grip", getPercentile(dto.grip, data.map { it.grip }))
            }
        return Member(
            name = dto.name,
            age = dto.age,
            sex = dto.sex,
            data = jsonObject,
        )
    }

    private fun getPercentile(
        score: Double,
        allScores: List<Double>,
    ): Double {
        val filteredScores = allScores.filter { it > 0 }
        if (filteredScores.isEmpty()) return 0.0

        val countLowerScores = filteredScores.count { it < score }

        val total = filteredScores.size

        return 100.0 * (1.0 - (countLowerScores.toDouble() / total))
    }
}
