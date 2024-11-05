package org.prize.healthapp.adapter.out.person

import kotlinx.coroutines.*
import org.prize.healthapp.application.port.out.PersonQuery
import org.prize.healthapp.domain.person.Person
import org.prize.healthapp.infrastructure.annotations.Adapter

@Adapter
class PersonAdapter(
    val personRepository: PersonRepository,
) : PersonQuery {
    @OptIn(ExperimentalCoroutinesApi::class)
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
}
