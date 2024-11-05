package org.prize.healthapp.adapter.out.testresult

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import org.prize.healthapp.adapter.out.common.BaseEntity
import org.prize.healthapp.domain.testresult.TestResult

@Entity
class TestResultEntity(
    age: Int,
    sex: String,
    data: JsonObject,
) : BaseEntity() {
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

    companion object {
        fun from(test: TestResult): TestResultEntity {
            val dataJsonObject = Json.encodeToJsonElement(test.data) as JsonObject
            return TestResultEntity(
                test.age,
                test.sex,
                dataJsonObject,
            )
        }
    }
}
