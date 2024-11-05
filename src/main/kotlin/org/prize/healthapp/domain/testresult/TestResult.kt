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
            var tests: List<TestResult>?
            runBlocking {
                // 병렬 처리에 사용할 스레드 풀 생성 (CPU 코어 수 기반)
                val dispatcher = Dispatchers.Default.limitedParallelism(4)
                tests =
                    csvData
                        .map { column ->
                            async(dispatcher) {
                                TestResult.of(
                                    column["MESURE_AGE_CO"]?.toIntOrNull() ?: 0,
                                    column["SEXDSTN_FLAG_CD"] ?: "",
                                    MeasurementData.from(column),
                                )
                            }
                        }.awaitAll()
            }
            return tests ?: throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }
    }
}
