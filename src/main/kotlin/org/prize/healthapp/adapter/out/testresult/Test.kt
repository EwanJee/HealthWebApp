package org.prize.healthapp.adapter.out.testresult

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@Entity
class Test(
    age: Int,
    sex: String,
    data: JsonObject,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
        protected set

    var age = age
        protected set

    var sex = sex
        protected set

    @Column(columnDefinition = "json")
    var data = Json.encodeToString(data)
        protected set
}
