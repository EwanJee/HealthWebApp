@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.person

import org.prize.healthapp.application.port.out.PersonQuery
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.person.Person
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.slf4j.Logger
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = false)
@Adapter
class PersonAdapter(
    private val personRepository: PersonRepository,
    private val jdbcTemplate: JdbcTemplate,
    private val logger: Logger,
) : PersonQuery {
    @Transactional
    override fun save(persons: List<Person>) {
        val personEntities = persons.map { PersonEntity.from(it) }
        val query = """INSERT INTO person (id, location, age) VALUES (UUID(), ?, ?)"""
        try {
            jdbcTemplate.batchUpdate(query, personEntities, personEntities.size) { ps, personEntity ->
                ps.setString(1, personEntity.location)
                ps.setInt(2, personEntity.age)
            }
        } catch (e: Exception) {
            logger.error("person bulk save failed", e)
            throw BusinessException(ErrorCode.TOO_MANY_REQUEST)
        }
    }

    override fun findAll(): List<Person> = personRepository.findAll().map { it.toPerson() }
}
