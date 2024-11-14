package org.prize.healthapp.application.service

import org.prize.healthapp.domain.testresult.MeasurementData
import java.math.BigDecimal
import java.math.RoundingMode

data class TestResultAvgDto(
    var ageSexDto: AgeSexDto,
    var height: Double = 0.0,
    var weight: Double = 0.0,
    var bodyFatPercentage: Double = 0.0,
    var sitUpCount: Double = 0.0,
    var bmiKgPerM2: Double = 0.0,
    var crossedSitUpCount: Double = 0.0,
    var shuttleRunCount: Double = 0.0,
    var tenMx4ShuttleRunSeconds: Double = 0.0,
    var standingLongJumpCm: Double = 0.0,
    var sitToStandCount: Double = 0.0,
    var twoMinuteSteppingInPlaceCount: Double = 0.0,
    var treadmill9MinutesBpm: Double = 0.0,
    var eightWalk: Double = 0.0,
    var reaction: Double = 0.0,
    var grip: Double = 0.0,
    // 각 필드의 유효 값 개수를 추적하는 카운터
    var heightCount: Int = 0,
    var weightCount: Int = 0,
    var bodyFatPercentageCount: Int = 0,
    var sitUpCountCount: Int = 0,
    var bmiKgPerM2Count: Int = 0,
    var crossedSitUpCountCount: Int = 0,
    var shuttleRunCountCount: Int = 0,
    var tenMx4ShuttleRunSecondsCount: Int = 0,
    var standingLongJumpCmCount: Int = 0,
    var sitToStandCountCount: Int = 0,
    var twoMinuteSteppingInPlaceCountCount: Int = 0,
    var treadmill9MinutesBpmCount: Int = 0,
    var eightWalkCount: Int = 0,
    var reactionCount: Int = 0,
    var gripCount: Int = 0,
) {
    companion object {
        fun of(
            ageSexDto: AgeSexDto,
            data: MeasurementData,
        ): TestResultAvgDto =
            TestResultAvgDto(
                ageSexDto = ageSexDto,
                height = data.height,
                weight = data.weight,
                bodyFatPercentage = data.bodyFatPercentage,
                sitUpCount = data.sitUpCount,
                bmiKgPerM2 = data.bmiKgPerM2,
                crossedSitUpCount = data.crossedSitUpCount,
                shuttleRunCount = data.shuttleRunCount,
                tenMx4ShuttleRunSeconds = data.tenMx4ShuttleRunSeconds,
                standingLongJumpCm = data.standingLongJumpCm,
                sitToStandCount = data.sitToStandCount,
                twoMinuteSteppingInPlaceCount = data.twoMinuteSteppingInPlaceCount,
                treadmill9MinutesBpm = data.treadmill9MinutesBpm,
                eightWalk = data.eightWalk,
                reaction = data.reaction,
                grip = data.grip,
            )
    }

    fun addData(data: TestResultAvgDto): TestResultAvgDto {
        listOf(
            TestResultAvgDto::height to TestResultAvgDto::heightCount,
            TestResultAvgDto::weight to TestResultAvgDto::weightCount,
            TestResultAvgDto::bodyFatPercentage to TestResultAvgDto::bodyFatPercentageCount,
            TestResultAvgDto::sitUpCount to TestResultAvgDto::sitUpCountCount,
            TestResultAvgDto::bmiKgPerM2 to TestResultAvgDto::bmiKgPerM2Count,
            TestResultAvgDto::crossedSitUpCount to TestResultAvgDto::crossedSitUpCountCount,
            TestResultAvgDto::shuttleRunCount to TestResultAvgDto::shuttleRunCountCount,
            TestResultAvgDto::tenMx4ShuttleRunSeconds to TestResultAvgDto::tenMx4ShuttleRunSecondsCount,
            TestResultAvgDto::standingLongJumpCm to TestResultAvgDto::standingLongJumpCmCount,
            TestResultAvgDto::sitToStandCount to TestResultAvgDto::sitToStandCountCount,
            TestResultAvgDto::twoMinuteSteppingInPlaceCount to TestResultAvgDto::twoMinuteSteppingInPlaceCountCount,
            TestResultAvgDto::treadmill9MinutesBpm to TestResultAvgDto::treadmill9MinutesBpmCount,
            TestResultAvgDto::eightWalk to TestResultAvgDto::eightWalkCount,
            TestResultAvgDto::reaction to TestResultAvgDto::reactionCount,
            TestResultAvgDto::grip to TestResultAvgDto::gripCount,
        ).forEach { (field, counter) ->
            val value = field.get(data) // `data`의 현재 필드 값 가져오기
            if (value != 0.0) {
                field.set(this, field.get(this) + value) // 기존 값에 새로운 값 더하기
                counter.set(this, counter.get(this) + 1) // 카운터 증가
            }
        }
        return this
    }

    fun divide() {
        fun Double.roundToTwoDecimals(): Double = BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
        if (heightCount > 0) height = (height / heightCount).roundToTwoDecimals()
        if (weightCount > 0) weight = (weight / weightCount).roundToTwoDecimals()
        if (bodyFatPercentageCount > 0) {
            bodyFatPercentage =
                (bodyFatPercentage / bodyFatPercentageCount).roundToTwoDecimals()
        }
        if (sitUpCountCount > 0) sitUpCount = (sitUpCount / sitUpCountCount).roundToTwoDecimals()
        if (bmiKgPerM2Count > 0) bmiKgPerM2 = (bmiKgPerM2 / bmiKgPerM2Count).roundToTwoDecimals()
        if (crossedSitUpCountCount > 0) {
            crossedSitUpCount =
                (crossedSitUpCount / crossedSitUpCountCount).roundToTwoDecimals()
        }
        if (shuttleRunCountCount > 0) shuttleRunCount = (shuttleRunCount / shuttleRunCountCount).roundToTwoDecimals()
        if (tenMx4ShuttleRunSecondsCount >
            0
        ) {
            tenMx4ShuttleRunSeconds = (tenMx4ShuttleRunSeconds / tenMx4ShuttleRunSecondsCount).roundToTwoDecimals()
        }
        if (standingLongJumpCmCount > 0) {
            standingLongJumpCm =
                (standingLongJumpCm / standingLongJumpCmCount).roundToTwoDecimals()
        }
        if (sitToStandCountCount > 0) sitToStandCount = (sitToStandCount / sitToStandCountCount).roundToTwoDecimals()
        if (twoMinuteSteppingInPlaceCountCount >
            0
        ) {
            twoMinuteSteppingInPlaceCount =
                (twoMinuteSteppingInPlaceCount / twoMinuteSteppingInPlaceCountCount).roundToTwoDecimals()
        }
        if (treadmill9MinutesBpmCount > 0) {
            treadmill9MinutesBpm =
                (treadmill9MinutesBpm / treadmill9MinutesBpmCount).roundToTwoDecimals()
        }
        if (eightWalkCount > 0) eightWalk = (eightWalk / eightWalkCount).roundToTwoDecimals()
        if (reactionCount > 0) reaction = (reaction / reactionCount).roundToTwoDecimals()
        if (gripCount > 0) grip = (grip / gripCount).roundToTwoDecimals()
    }
}
