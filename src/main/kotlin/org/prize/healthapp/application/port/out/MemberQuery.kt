package org.prize.healthapp.application.port.out

import org.prize.healthapp.domain.member.Member
import java.util.UUID

interface MemberQuery {
    fun save(member: Member): UUID

    fun findById(id: UUID): Member
}
