@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.domain.person

import kotlinx.coroutines.*
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode

class Person(
    val location: String,
    val age: Int,
) {
    companion object {
        fun of(
            location: String,
            age: Int,
        ): Person = Person(location, age)

        @OptIn(ExperimentalCoroutinesApi::class)
        fun from(csvData: List<Map<String, String>>): List<Person> {
            var persons: List<Person>?
            runBlocking {
                // 병렬 처리에 사용할 스레드 풀 생성 (CPU 코어 수 기반)
                val dispatcher = Dispatchers.Default.limitedParallelism(4)

                // 각 CSV 데이터를 비동기로 변환하여 리스트로 만듦
                persons =
                    csvData
                        .map { data ->
                            async(dispatcher) {
                                Person.of(
                                    data["CNTER_NM"] ?: "",
                                    data["MESURE_AGE_CO"]?.toIntOrNull() ?: 0,
                                )
                            }
                        }.awaitAll()
            }
            return persons ?: throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }
    }
}
