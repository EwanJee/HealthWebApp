package org.prize.healthapp.application.service

import org.prize.healthapp.application.port.`in`.StadiumCommand
import org.prize.healthapp.application.port.out.ClientQuery
import org.prize.healthapp.application.service.dto.StadiumItem
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StadiumService(
    private val clientQuery: ClientQuery,
) : StadiumCommand {
    override suspend fun getBy(
        city: String,
        district: String,
        pageable: Pageable,
    ): Page<StadiumResponseDto> {
        val response: List<StadiumItem> = clientQuery.getBy(city, district)
        val dtos: List<StadiumResponseDto> =
            response.mapNotNull { item ->
                var addr = item.faci_addr
                if (addr.isEmpty()) {
                    addr = item.faci_road_addr
                    if (addr.isEmpty()) {
                        addr = item.faci_daddr
                    }
                }
                if (addr.isEmpty()) {
                    null // addr이 비어 있으면 null 반환하여 리스트에 포함하지 않음
                } else {
                    StadiumResponseDto(
                        address = addr,
                        telNumber = item.faci_tel_no,
                        type = item.ftype_nm,
                    )
                }
            }
        val start = (pageable.pageNumber * pageable.pageSize).coerceAtMost(dtos.size)
        val end = (start + pageable.pageSize).coerceAtMost(dtos.size)
        val pagedContent = dtos.subList(start, end)
        return PageImpl(pagedContent, pageable, dtos.size.toLong())
    }
}
