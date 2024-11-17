@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.domain.base64

import java.nio.ByteBuffer
import java.util.*

fun UUID.toBase64(): String {
    val byteArray =
        ByteBuffer
            .allocate(16)
            .putLong(this.mostSignificantBits)
            .putLong(this.leastSignificantBits)
            .array()
    return Base64.getEncoder().encodeToString(byteArray)
}

fun String.toUUID(): UUID {
    try {
        val dec = Base64.getDecoder().decode(this)

        if (dec.size != 16) {
            throw IllegalArgumentException("UUIDs can only be created from 128bit")
        }
        val buf = ByteBuffer.wrap(dec)
        return UUID(buf.getLong(), buf.getLong())
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid Base64 sequence", e)
    }
}
