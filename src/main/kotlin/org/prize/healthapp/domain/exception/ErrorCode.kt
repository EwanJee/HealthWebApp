package org.prize.healthapp.domain.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    // S3 FILE NOT FOUND
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "관련 파일을 찾을 수 없습니다."),

    // Wrong File Format
    WRONG_FILE_FORMAT(HttpStatus.BAD_REQUEST, "F002", "잘못된 파일 형식입니다."),

    // TOO MANY REQUEST
    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "F003", "요청이 너무 많습니다."),

    // WRONG FILE NAME
    WRONG_FILE_NAME(HttpStatus.BAD_REQUEST, "F004", "잘못된 파일 이름입니다."),

    // MISSING COLUMN
    MISSING_COLUMN(HttpStatus.BAD_REQUEST, "F005", "필수 칼럼이 누락되었습니다."),
}
