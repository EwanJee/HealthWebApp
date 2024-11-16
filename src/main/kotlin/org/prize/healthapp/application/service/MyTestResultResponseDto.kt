package org.prize.healthapp.application.service

import kotlinx.serialization.Serializable

@Serializable
data class MyTestResultResponseDto(
    // each test result column is percentage value.
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
    val eightWalk: Double? = null,
    val reaction: Double? = null,
    val grip: Double? = null,
)