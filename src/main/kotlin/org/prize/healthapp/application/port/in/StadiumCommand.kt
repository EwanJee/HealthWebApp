@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.application.service.StadiumResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StadiumCommand {
    suspend fun getBy(
        city: String,
        district: String,
        pageable: Pageable,
    ): Page<StadiumResponseDto>
}
