package org.prize.healthapp.domain.exception

class BusinessException(
    val errorCode: ErrorCode,
    private val exception: Throwable? = null,
) : RuntimeException(
        errorCode.message,
        exception,
    )
