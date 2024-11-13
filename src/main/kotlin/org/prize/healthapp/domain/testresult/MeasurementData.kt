package org.prize.healthapp.domain.testresult

import kotlinx.serialization.Serializable

@Serializable
data class MeasurementData(
    val height: Double = 0.0, // 키
    val weight: Double = 0.0, // 체중
    val bodyFatPercentage: Double = 0.0, // 체지방률
    val sitUpCount: Double = 0.0, // 앉아 윗몸 말아 올리기
    val bmiKgPerM2: Double = 0.0, // BMI
    val crossedSitUpCount: Double = 0.0, // 교차 윗몸일으키기
    val shuttleRunCount: Double = 0.0, // 왕복 오래 달리기
    val tenMx4ShuttleRunSeconds: Double = 0.0, // 10m 4회 왕복 오래 달리기
    val standingLongJumpCm: Double = 0.0, // 제자리 멀리 뛰기
    val sitToStandCount: Double = 0.0, // 앉았다 일어서기
    val twoMinuteSteppingInPlaceCount: Double = 0.0, // 2분간 제자리 걷기
    val treadmill9MinutesBpm: Double = 0.0, // 9분간 뛰기
    val eightWalk: Double = 0.0, // 허벅지 둘레 (좌) -> 8자 보행
    val reaction: Double = 0.0, // 허벅지 둘레 (우) -> 반응 시간
    val grip: Double = 0.0, // 5m 4회 왕복 오래 달리기 -> 악력
) {
    companion object {
        private fun getDoubleValue(
            column: Map<String, String>,
            key: String,
        ): Double = column[key]?.toDoubleOrNull() ?: 0.0

        fun from(column: Map<String, String>): MeasurementData =
            MeasurementData(
                height = getDoubleValue(column, "MESURE_IEM_001_VALUE"),
                weight = getDoubleValue(column, "MESURE_IEM_002_VALUE"),
                bodyFatPercentage = getDoubleValue(column, "MESURE_IEM_003_VALUE"),
                sitUpCount = getDoubleValue(column, "MESURE_IEM_012_VALUE"),
                bmiKgPerM2 = getDoubleValue(column, "MESURE_IEM_018_VALUE"),
                crossedSitUpCount = getDoubleValue(column, "MESURE_IEM_019_VALUE"),
                shuttleRunCount = getDoubleValue(column, "MESURE_IEM_020_VALUE"),
                tenMx4ShuttleRunSeconds = getDoubleValue(column, "MESURE_IEM_021_VALUE"),
                standingLongJumpCm = getDoubleValue(column, "MESURE_IEM_022_VALUE"),
                sitToStandCount = getDoubleValue(column, "MESURE_IEM_023_VALUE"),
                twoMinuteSteppingInPlaceCount = getDoubleValue(column, "MESURE_IEM_025_VALUE"),
                treadmill9MinutesBpm = getDoubleValue(column, "MESURE_IEM_034_VALUE"),
                eightWalk = getDoubleValue(column, "MESURE_IEM_027_VALUE"),
                reaction = getDoubleValue(column, "MESURE_IEM_040_VALUE"),
                grip = getDoubleValue(column, "MESURE_IEM_008_VALUE"),
            )
    }
}
