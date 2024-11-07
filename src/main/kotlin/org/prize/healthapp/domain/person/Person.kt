@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.domain.person

import kotlinx.coroutines.*
import org.prize.healthapp.application.service.PersonDistributionDto
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
                                // 필수 칼럼 체크
                                val cnterNm =
                                    data["CNTER_NM"] ?: throw BusinessException(
                                        ErrorCode.MISSING_COLUMN,
                                    )
                                val mesureAgeCo =
                                    data["MESURE_AGE_CO"]?.toIntOrNull() ?: throw BusinessException(
                                        ErrorCode.MISSING_COLUMN,
                                    )

                                Person.of(
                                    cnterNm,
                                    mesureAgeCo,
                                )
                            }
                        }.awaitAll()
            }
            return persons ?: throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }

        fun convert(persons: List<Person>): List<PersonDistributionDto> {
            val personDistributionDto =
                persons
                    .groupBy { it.location }
                    .map { (location, persons) ->
                        PersonDistributionDto(
                            location = location,
                            count = persons.size,
                            age10s = persons.count { it.age in 10..19 },
                            age20s = persons.count { it.age in 20..29 },
                            age30s = persons.count { it.age in 30..39 },
                            age40s = persons.count { it.age in 40..49 },
                            age50s = persons.count { it.age in 50..59 },
                            age60s = persons.count { it.age in 60..69 },
                            age70s = persons.count { it.age in 70..79 },
                            age80s = persons.count { it.age in 80..89 },
                            age90s = persons.count { it.age in 90..99 },
                        )
                    }
            return personDistributionDto
        }
    }
}
