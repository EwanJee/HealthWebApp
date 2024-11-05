package org.prize.healthapp.application.port.out

import org.prize.healthapp.domain.person.Person

interface PersonQuery {
    fun save(persons: List<Person>)
}
