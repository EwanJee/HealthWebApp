package org.prize.healthapp.adapter.out.person

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.prize.healthapp.domain.person.Person

@Table(name = "person")
@Entity
class PersonEntity(
    location: String,
    age: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        protected set

    var location = location
        protected set

    var age = age
        protected set

    companion object {
        fun from(person: Person): PersonEntity = PersonEntity(person.location, person.age)
    }
}
