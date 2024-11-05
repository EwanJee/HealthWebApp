@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.testresult

import kotlinx.coroutines.*
import org.prize.healthapp.application.port.out.TestQuery
import org.prize.healthapp.domain.testresult.TestResult
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = false)
@Adapter
class TestResultAdapter(
    private val testResultRepository: TestResultRepository,
) : TestQuery {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Transactional
    override fun save(tests: List<TestResult>) {
        runBlocking {
            val dispatcher = Dispatchers.Default.limitedParallelism(4)
            tests
                .map { test ->
                    async(dispatcher) {
                        testResultRepository.save(TestResultEntity.from(test))
                    }
                }.awaitAll()
        }
    }
}
