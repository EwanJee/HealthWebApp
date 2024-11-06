@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.person

import jakarta.persistence.*
import org.prize.healthapp.adapter.out.common.BaseEntity
import org.prize.healthapp.domain.person.Person
import java.util.UUID

@Table(name = "person")
@Entity
class PersonEntity(
    location: String,
    age: Int,
) : BaseEntity() {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    var id: UUID? = null

    @Column(nullable = false)
    var location = location
        protected set

    @Column(nullable = false)
    var age = age
        protected set

    companion object {
        fun from(person: Person): PersonEntity = PersonEntity(person.location, person.age)
    }

    fun toPerson(): Person = Person(location, age)
}
