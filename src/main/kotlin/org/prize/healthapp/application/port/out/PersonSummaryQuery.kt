package org.prize.healthapp.application.port.out

import org.prize.healthapp.application.service.PersonDistributionDto

interface PersonSummaryQuery {
    fun distribute(persons: List<PersonDistributionDto>)

    fun findAll(): List<PersonDistributionDto>
}
