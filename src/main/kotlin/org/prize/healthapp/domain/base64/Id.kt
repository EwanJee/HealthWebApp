@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.domain.base64

import java.util.*

class Id {
    companion object {
        fun base64ToUuid(base64: String): UUID {
            // Base64 문자열을 바이트 배열로 디코딩
            val uuidBytes = Base64.getUrlDecoder().decode(base64)
            val byteBuffer = java.nio.ByteBuffer.wrap(uuidBytes)

            // 바이트 배열을 UUID로 변환
            val mostSignificantBits = byteBuffer.long
            val leastSignificantBits = byteBuffer.long
            return UUID(mostSignificantBits, leastSignificantBits)
        }

        fun uuidToBase64(uuid: UUID): String {
            // UUID를 바이트 배열로 변환
            val uuidBytes = ByteArray(16)
            val byteBuffer = java.nio.ByteBuffer.wrap(uuidBytes)
            byteBuffer.putLong(uuid.mostSignificantBits)
            byteBuffer.putLong(uuid.leastSignificantBits)

            // 바이트 배열을 Base64로 인코딩
            return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidBytes)
        }
    }
}
