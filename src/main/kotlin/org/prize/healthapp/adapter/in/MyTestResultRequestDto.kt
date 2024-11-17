@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.adapter.`in`

data class MyTestResultRequestDto(
    val name: String,
    val age: Int,
    val sex: String,
    val height: Double,
    val weight: Double,
    val bodyFatPercentage: Double,
    val sitUpCount: Double,
    val bmiKgPerM2: Double,
    val crossedSitUpCount: Double,
    val shuttleRunCount: Double,
    val tenMx4ShuttleRunSeconds: Double,
    val standingLongJumpCm: Double,
    val sitToStandCount: Double,
    val twoMinuteSteppingInPlaceCount: Double,
    val treadmill9MinutesBpm: Double,
    val eightWalk: Double,
    val reaction: Double,
    val grip: Double,
)
