package org.prize.healthapp.application.service

data class PersonDistributionDto(
    var location: String,
    var count: Int,
    var age10s: Int,
    var age20s: Int,
    var age30s: Int,
    var age40s: Int,
    var age50s: Int,
    var age60s: Int,
    var age70s: Int,
    var age80s: Int,
    var age90s: Int,
)
