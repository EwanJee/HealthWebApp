package org.prize.healthapp.adapter.out.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MemberRepository : JpaRepository<MemberEntity, UUID>
