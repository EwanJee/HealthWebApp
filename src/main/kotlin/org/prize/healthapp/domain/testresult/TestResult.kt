package org.prize.healthapp.domain.testresult

class TestResult(
    val age: Int,
    val sex: String,
    val data: MeasurementData,
) {
    companion object {
        fun of(
            age: Int,
            sex: String,
            data: MeasurementData,
        ): TestResult = TestResult(age, sex, data)
    }
}
