@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

data class MyTestResultRequestDto(
    val age: Int,
    val sex: String,
    val height: Double? = null,
    val weight: Double? = null,
    val bodyFatPercentage: Double? = null,
    val sitUpCount: Double? = null,
    val bmiKgPerM2: Double? = null,
    val crossedSitUpCount: Double? = null,
    val shuttleRunCount: Double? = null,
    val tenMx4ShuttleRunSeconds: Double? = null,
    val standingLongJumpCm: Double? = null,
    val sitToStandCount: Double? = null,
    val twoMinuteSteppingInPlaceCount: Double? = null,
    val treadmill9MinutesBpm: Double? = null,
    val thighCircumferenceLeftCm: Double? = null,
    val thighCircumferenceRightCm: Double? = null,
    val fiveMx4ShuttleRunSeconds: Double? = null,
)
