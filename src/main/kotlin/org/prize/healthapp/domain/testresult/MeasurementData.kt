package org.prize.healthapp.domain.testresult

import kotlinx.serialization.Serializable

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
        fun from(column: Map<String, String>): MeasurementData =
            MeasurementData(
                height = column["MESURE_IEM_001_VALUE"]?.toDoubleOrNull() ?: 0.0,
                weight = column["MESURE_IEM_002_VALUE"]?.toDoubleOrNull() ?: 0.0,
                bodyFatPercentage = column["MESURE_IEM_003_VALUE"]?.toDoubleOrNull() ?: 0.0,
                sitUpCount = column["MESURE_IEM_010_VALUE"]?.toDoubleOrNull() ?: 0.0,
                bmiKgPerM2 = column["MESURE_IEM_018_VALUE"]?.toDoubleOrNull() ?: 0.0,
                crossedSitUpCount = column["MESURE_IEM_019_VALUE"]?.toDoubleOrNull() ?: 0.0,
                shuttleRunCount = column["MESURE_IEM_020_VALUE"]?.toDoubleOrNull() ?: 0.0,
                tenMx4ShuttleRunSeconds = column["MESURE_IEM_021_VALUE"]?.toDoubleOrNull() ?: 0.0,
                standingLongJumpCm = column["STANDING_LONG_JUMP_CM"]?.toDoubleOrNull() ?: 0.0,
                sitToStandCount = column["MESURE_IEM_022_VALUE"]?.toDoubleOrNull() ?: 0.0,
                twoMinuteSteppingInPlaceCount = column["MESURE_IEM_025_VALUE"]?.toDoubleOrNull() ?: 0.0,
                treadmill9MinutesBpm = column["MESURE_IEM_034_VALUE"]?.toDoubleOrNull() ?: 0.0,
                thighCircumferenceLeftCm = column["MESURE_IEM_038_VALUE"]?.toDoubleOrNull() ?: 0.0,
                thighCircumferenceRightCm = column["MESURE_IEM_039_VALUE"]?.toDoubleOrNull() ?: 0.0,
                fiveMx4ShuttleRunSeconds = column["MESURE_IEM_050_VALUE"]?.toDoubleOrNull() ?: 0.0,
            )
    }
}
