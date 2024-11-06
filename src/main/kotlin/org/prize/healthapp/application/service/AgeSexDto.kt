package org.prize.healthapp.application.service

data class AgeSexDto(
    val ages: Int,
    val sex: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AgeSexDto

        if (ages != other.ages) return false
        if (sex != other.sex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ages
        result = 31 * result + sex.hashCode()
        return result
    }
}
