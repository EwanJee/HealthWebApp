package org.prize.healthapp.domain.testresult

import kotlinx.serialization.Serializable
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode

@Serializable
data class MeasurementData(
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val bodyFatPercentage: Double = 0.0,
    val sitUpCount: Double = 0.0,
    val bmiKgPerM2: Double = 0.0,
    val crossedSitUpCount: Double = 0.0,
    val shuttleRunCount: Double = 0.0,
    val tenMx4ShuttleRunSeconds: Double = 0.0,
    val standingLongJumpCm: Double = 0.0,
    val sitToStandCount: Double = 0.0,
    val twoMinuteSteppingInPlaceCount: Double = 0.0,
    val treadmill9MinutesBpm: Double = 0.0,
    val thighCircumferenceLeftCm: Double = 0.0,
    val thighCircumferenceRightCm: Double = 0.0,
    val fiveMx4ShuttleRunSeconds: Double = 0.0,
) {
    companion object {
        private fun getDoubleValue(
            column: Map<String, String>,
            key: String,
        ): Double = column[key]?.toDoubleOrNull() ?: throw BusinessException(ErrorCode.MISSING_COLUMN)

        fun from(column: Map<String, String>): MeasurementData =
            MeasurementData(
                height = getDoubleValue(column, "MESURE_IEM_001_VALUE"),
                weight = getDoubleValue(column, "MESURE_IEM_002_VALUE"),
                bodyFatPercentage = getDoubleValue(column, "MESURE_IEM_003_VALUE"),
                sitUpCount = getDoubleValue(column, "MESURE_IEM_010_VALUE"),
                bmiKgPerM2 = getDoubleValue(column, "MESURE_IEM_018_VALUE"),
                crossedSitUpCount = getDoubleValue(column, "MESURE_IEM_019_VALUE"),
                shuttleRunCount = getDoubleValue(column, "MESURE_IEM_020_VALUE"),
                tenMx4ShuttleRunSeconds = getDoubleValue(column, "MESURE_IEM_021_VALUE"),
                standingLongJumpCm = getDoubleValue(column, "MESURE_IEM_022_VALUE"),
                sitToStandCount = getDoubleValue(column, "MESURE_IEM_023_VALUE"),
                twoMinuteSteppingInPlaceCount = getDoubleValue(column, "MESURE_IEM_025_VALUE"),
                treadmill9MinutesBpm = getDoubleValue(column, "MESURE_IEM_034_VALUE"),
                thighCircumferenceLeftCm = getDoubleValue(column, "MESURE_IEM_038_VALUE"),
                thighCircumferenceRightCm = getDoubleValue(column, "MESURE_IEM_039_VALUE"),
                fiveMx4ShuttleRunSeconds = getDoubleValue(column, "MESURE_IEM_050_VALUE"),
            )
    }
}
