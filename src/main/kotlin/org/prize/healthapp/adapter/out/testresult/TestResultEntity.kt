@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.testresult

import jakarta.persistence.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import org.prize.healthapp.adapter.out.common.BaseEntity
import org.prize.healthapp.application.service.MyTestResultResponseDto
import org.prize.healthapp.domain.testresult.MeasurementData
import org.prize.healthapp.domain.testresult.TestResult
import java.util.UUID

@Table(name = "test_result")
@Entity
class TestResultEntity(
    age: Int,
    sex: String,
    data: JsonObject,
) : BaseEntity() {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    var id: UUID? = null

    var age = age
        protected set

    var sex = sex
        protected set

    @Column(columnDefinition = "json")
    var data = Json.encodeToString(data)
        protected set

    fun toTestResult(): TestResult {
        val measurementData = Json.decodeFromString<MeasurementData>(data)
        return TestResult(
            age = age,
            sex = sex,
            data = measurementData,
        )
    }

    fun toMyTestResultResponseDto(): MyTestResultResponseDto {
        val measurementData = Json.decodeFromString<MeasurementData>(data)
        return MyTestResultResponseDto(
            age,
            sex,
            measurementData.height,
            measurementData.weight,
            measurementData.bodyFatPercentage,
            measurementData.sitUpCount,
            measurementData.bmiKgPerM2,
            measurementData.crossedSitUpCount,
            measurementData.shuttleRunCount,
            measurementData.tenMx4ShuttleRunSeconds,
            measurementData.standingLongJumpCm,
            measurementData.sitToStandCount,
            measurementData.twoMinuteSteppingInPlaceCount,
            measurementData.treadmill9MinutesBpm,
            measurementData.eightWalk,
            measurementData.reaction,
            measurementData.grip,
        )
    }

    companion object {
        fun from(test: TestResult): TestResultEntity {
            val dataJsonObject = Json.encodeToJsonElement(test.data) as JsonObject
            return TestResultEntity(
                test.age,
                test.sex,
                dataJsonObject,
            )
        }

        fun from(test: MyTestResultResponseDto): TestResultEntity {
            val json = Json.encodeToJsonElement(test) as JsonObject
            val dataJsonObject =
                buildJsonObject {
                    json.filterKeys { it !in setOf("age", "sex") }.forEach { (key, value) ->
                        put(key, value)
                    }
                }
            return TestResultEntity(
                test.age,
                test.sex,
                dataJsonObject,
            )
        }
    }
}
