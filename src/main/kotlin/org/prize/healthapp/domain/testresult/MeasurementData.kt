package org.prize.healthapp.domain.testresult

import kotlinx.serialization.Serializable
import org.apache.commons.csv.CSVRecord

@Serializable
data class MeasurementData(
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val bodyFatPercentage: Double = 0.0,
    val sitUpCount: Int = 0,
    val bmiKgPerM2: Double = 0.0,
    val crossedSitUpCount: Int = 0,
    val shuttleRunCount: Int = 0,
    val tenMx4ShuttleRunSeconds: Double = 0.0,
    val standingLongJumpCm: Int = 0,
    val sitToStandCount: Int = 0,
    val twoMinuteSteppingInPlaceCount: Int = 0,
    val treadmill9MinutesBpm: Int = 0,
    val thighCircumferenceLeftCm: Double = 0.0,
    val thighCircumferenceRightCm: Double = 0.0,
    val fiveMx4ShuttleRunSeconds: Double = 0.0,
) {
    companion object {
        fun from(columns: CSVRecord): MeasurementData =
            MeasurementData(
                columns[9].toDouble(),
                columns[10].toDouble(),
                columns[11].toDouble(),
                columns[18].toInt(),
                columns[25].toDouble(),
                columns[26].toInt(),
                columns[27].toInt(),
                columns[28].toDouble(),
                columns[29].toInt(),
                columns[30].toInt(),
                columns[32].toInt(),
                columns[41].toInt(),
                columns[45].toDouble(),
                columns[46].toDouble(),
                columns[52].toDouble(),
            )
    }
}
