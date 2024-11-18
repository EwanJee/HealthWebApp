package org.prize.healthapp.application.port.out

import org.prize.healthapp.application.service.dto.StadiumItem

interface ClientQuery {
    suspend fun getBy(
        city: String,
        district: String,
    ): List<StadiumItem>
}
