package org.prize.healthapp.domain.member

import kotlinx.serialization.json.JsonObject

data class Member(
    // each test result column is percentage value.
    val name: String,
    val age: Int,
    val sex: String,
    val data: JsonObject,
)
