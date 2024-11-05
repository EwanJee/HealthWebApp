@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.person

import jakarta.persistence.*
import org.prize.healthapp.application.service.PersonDistributionDto

@Table(name = "person_summary")
@Entity
class PersonSummaryEntity(
    location: String,
    personCount: Int,
    age10s: Int,
    age20s: Int,
    age30s: Int,
    age40s: Int,
    age50s: Int,
    age60s: Int,
    age70s: Int,
    age80s: Int,
    age90s: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null
        protected set

    @Column(name = "location", unique = true, nullable = false)
    var location: String? = location
        protected set

    @Column(name = "total_persons", nullable = false)
    var personCount: Int = personCount
        protected set

    var age10s = age10s
        protected set
    var age20s = age20s
        protected set
    var age30s = age30s
        protected set
    var age40s = age40s
        protected set
    var age50s = age50s
        protected set
    var age60s = age60s
        protected set
    var age70s = age70s
        protected set
    var age80s = age80s
        protected set
    var age90s = age90s
        protected set

    companion object {
        fun from(personSummary: PersonDistributionDto): PersonSummaryEntity =
            PersonSummaryEntity(
                personSummary.location,
                personSummary.count,
                personSummary.age10s,
                personSummary.age20s,
                personSummary.age30s,
                personSummary.age40s,
                personSummary.age50s,
                personSummary.age60s,
                personSummary.age70s,
                personSummary.age80s,
                personSummary.age90s,
            )
    }

    fun update(it: PersonDistributionDto) {
        personCount += it.count
        age10s = it.age10s
        age20s = it.age20s
        age30s = it.age30s
        age40s = it.age40s
        age50s = it.age50s
        age60s = it.age60s
        age70s = it.age70s
        age80s = it.age80s
        age90s = it.age90s
    }

    fun toPersonDistributionDto(): PersonDistributionDto =
        PersonDistributionDto(
            location = location!!,
            count = personCount,
            age10s = age10s,
            age20s = age20s,
            age30s = age30s,
            age40s = age40s,
            age50s = age50s,
            age60s = age60s,
            age70s = age70s,
            age80s = age80s,
            age90s = age90s,
        )
}
