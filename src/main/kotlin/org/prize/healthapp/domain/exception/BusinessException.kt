package org.prize.healthapp.domain.exception

class BusinessException(
    val errorCode: ErrorCode,
    var exception: Throwable? = null,
) : RuntimeException(
        errorCode.message,
        exception,
    )
