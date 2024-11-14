package org.prize.healthapp.adapter.out.testresult

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TestResultRepository : JpaRepository<TestResultEntity, UUID> {
    fun findByAgeAndSex(
        age: Int,
        sex: String,
    ): List<TestResultEntity>
}
