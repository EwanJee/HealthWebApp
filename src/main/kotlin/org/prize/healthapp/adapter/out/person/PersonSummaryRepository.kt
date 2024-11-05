package org.prize.healthapp.adapter.out.person

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonSummaryRepository : JpaRepository<PersonSummaryEntity, Long> {
    fun findByLocation(location: String): PersonSummaryEntity?
}
