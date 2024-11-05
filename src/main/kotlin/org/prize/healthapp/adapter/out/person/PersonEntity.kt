package org.prize.healthapp.adapter.out.person

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.prize.healthapp.adapter.out.common.BaseEntity
import org.prize.healthapp.domain.person.Person

@Table(name = "person")
@Entity
class PersonEntity(
    location: String,
    age: Int,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        protected set

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
