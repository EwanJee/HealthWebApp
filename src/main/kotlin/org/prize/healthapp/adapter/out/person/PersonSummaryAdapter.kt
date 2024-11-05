package org.prize.healthapp.adapter.out.person

import org.prize.healthapp.application.port.out.PersonSummaryQuery
import org.prize.healthapp.application.service.PersonDistributionDto
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = false)
@Adapter
class PersonSummaryAdapter(
    val personSummaryRepository: PersonSummaryRepository,
) : PersonSummaryQuery {
    @Transactional
    override fun distribute(persons: List<PersonDistributionDto>) {
        persons.map {
            val location = it.location
            val personSummaryEntity = personSummaryRepository.findByLocation(location)
            personSummaryEntity?.update(it) ?: personSummaryRepository.save(PersonSummaryEntity.from(it))
        }
    }

    override fun findAll(): List<PersonDistributionDto> = personSummaryRepository.findAll().map { it.toPersonDistributionDto() }
}
