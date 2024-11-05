@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.person

import kotlinx.coroutines.*
import org.prize.healthapp.application.port.out.PersonQuery
import org.prize.healthapp.domain.person.Person
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = false)
@Adapter
class PersonAdapter(
    val personRepository: PersonRepository,
) : PersonQuery {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Transactional
    override fun save(persons: List<Person>): Unit =
        runBlocking {
            val dispatcher = Dispatchers.Default.limitedParallelism(4)
            persons
                .map { person ->
                    async(dispatcher) {
                        personRepository.save(PersonEntity.from(person))
                    }
                }.awaitAll()
        }

    override fun findAll(): List<Person> = personRepository.findAll().map { it.toPerson() }
}
