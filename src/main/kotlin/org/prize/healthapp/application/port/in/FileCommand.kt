@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.out.s3.FileUploadResponseDto
import org.springframework.web.multipart.MultipartFile

interface FileCommand {
    fun uploadPersons(multiPartFile: MultipartFile): FileUploadResponseDto

    fun uploadTests(multiPartFile: MultipartFile): FileUploadResponseDto
}
