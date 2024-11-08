package org.prize.healthapp.application.service

import org.prize.healthapp.domain.testresult.MeasurementData

data class TestResultAvgDto(
    var ageSexDto: AgeSexDto,
    var height: Double,
    var weight: Double,
    var bodyFatPercentage: Double,
    var sitUpCount: Double,
    var bmiKgPerM2: Double,
    var crossedSitUpCount: Double,
    var shuttleRunCount: Double,
    var tenMx4ShuttleRunSeconds: Double,
    var standingLongJumpCm: Double,
    var sitToStandCount: Double,
    var twoMinuteSteppingInPlaceCount: Double,
    var treadmill9MinutesBpm: Double,
    var thighCircumferenceLeftCm: Double,
    var thighCircumferenceRightCm: Double,
    var fiveMx4ShuttleRunSeconds: Double,
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
                thighCircumferenceLeftCm = data.thighCircumferenceLeftCm,
                thighCircumferenceRightCm = data.thighCircumferenceRightCm,
                fiveMx4ShuttleRunSeconds = data.fiveMx4ShuttleRunSeconds,
            )
    }

    fun addData(data: TestResultAvgDto): TestResultAvgDto {
        height += data.height
        weight += data.weight
        bodyFatPercentage += data.bodyFatPercentage
        sitUpCount += data.sitUpCount
        bmiKgPerM2 += data.bmiKgPerM2
        crossedSitUpCount += data.crossedSitUpCount
        shuttleRunCount += data.shuttleRunCount
        tenMx4ShuttleRunSeconds += data.tenMx4ShuttleRunSeconds
        standingLongJumpCm += data.standingLongJumpCm
        sitToStandCount += data.sitToStandCount
        twoMinuteSteppingInPlaceCount += data.twoMinuteSteppingInPlaceCount
        treadmill9MinutesBpm += data.treadmill9MinutesBpm
        thighCircumferenceLeftCm += data.thighCircumferenceLeftCm
        thighCircumferenceRightCm += data.thighCircumferenceRightCm
        fiveMx4ShuttleRunSeconds += data.fiveMx4ShuttleRunSeconds
        return this
    }

    fun divide(count: Int) {
        height /= count
        weight /= count
        bodyFatPercentage /= count
        sitUpCount /= count
        bmiKgPerM2 /= count
        crossedSitUpCount /= count
        shuttleRunCount /= count
        tenMx4ShuttleRunSeconds /= count
        standingLongJumpCm /= count
        sitToStandCount /= count
        twoMinuteSteppingInPlaceCount /= count
        treadmill9MinutesBpm /= count
        thighCircumferenceLeftCm /= count
        thighCircumferenceRightCm /= count
        fiveMx4ShuttleRunSeconds /= count
    }
}
