package org.prize.healthapp.adapter.out.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.prize.healthapp.adapter.out.common.BaseEntity
import org.prize.healthapp.domain.member.Member
import java.util.UUID

@Table(name = "member")
@Entity
class MemberEntity(
    id: UUID,
    name: String,
    age: Int,
    sex: String,
    data: JsonObject,
) : BaseEntity() {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    var id: UUID? = id
        protected set

    var name: String = name
        protected set
    var age: Int = age
        protected set
    var sex: String = sex
        protected set

    @Column(columnDefinition = "json")
    var data = Json.encodeToString(data)
        protected set

    fun toMember(): Member {
        val jsonData = Json.decodeFromString(JsonObject.serializer(), this.data)
        return Member(
            name,
            age,
            sex,
            jsonData,
        )
    }

    companion object {
        fun from(
            member: Member,
            uuid: UUID,
        ): MemberEntity =
            MemberEntity(
                uuid,
                member.name,
                member.age,
                member.sex,
                member.data,
            )
    }
}
