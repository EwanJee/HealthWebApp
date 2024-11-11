@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.domain.testresult

import kotlinx.coroutines.*
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode

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

        @OptIn(ExperimentalCoroutinesApi::class)
        fun from(csvData: List<Map<String, String>>): List<TestResult> {
            var tests: List<TestResult>? = mutableListOf()
            runBlocking {
                // 병렬 처리에 사용할 스레드 풀 생성 (CPU 코어 수 기반)
                val dispatcher = Dispatchers.Default.limitedParallelism(4)
                tests =
                    csvData
                        .map { column ->
                            async(dispatcher) {
                                val age =
                                    column["MESURE_AGE_CO"]?.toInt()
                                        ?: throw BusinessException(ErrorCode.MISSING_COLUMN, Throwable("MESURE_AGE_CO"))
                                val sex =
                                    column["SEXDSTN_FLAG_CD"]
                                        ?: throw BusinessException(
                                            ErrorCode.MISSING_COLUMN,
                                            Throwable(
                                                @Suppress("ktlint:standard:max-line-length")
                                                "SEXDSTN_FLAG_CD",
                                            ),
                                        )
                                TestResult.of(
                                    getAgeGroup(age),
                                    sex,
                                    MeasurementData.from(column),
                                )
                            }
                        }.awaitAll()
            }
            return tests ?: throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }

        private fun getAgeGroup(age: Int): Int =
            when (age) {
                in 10..19 -> 10
                in 20..29 -> 20
                in 30..39 -> 30
                in 40..49 -> 40
                in 50..59 -> 50
                in 60..69 -> 60
                in 70..79 -> 70
                in 80..89 -> 80
                in 90..150 -> 90
                else -> {
                    throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
                }
            }
    }
}
