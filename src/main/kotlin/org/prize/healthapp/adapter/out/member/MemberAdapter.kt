@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.member

import org.prize.healthapp.application.port.out.MemberQuery
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.member.Member
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional(readOnly = false)
@Adapter
class MemberAdapter(
    private val memberRepository: MemberRepository,
) : MemberQuery {
    @Transactional
    override fun save(member: Member): UUID {
        val uuid = UUID.randomUUID()
        memberRepository.save(MemberEntity.from(member, uuid))
        return uuid
    }

    override fun findById(id: UUID): Member {
        val memberEntity =
            memberRepository
                .findById(id)
                .orElseThrow { BusinessException(ErrorCode.NOT_FOUND) }
        return memberEntity.toMember()
    }
}
