package org.prize.healthapp.adapter.out.testresult

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestResultRepository : JpaRepository<TestResultEntity, Long>
