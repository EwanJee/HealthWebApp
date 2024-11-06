@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.springframework.web.multipart.MultipartFile

interface FileCommand {
    fun uploadPersons(multiPartFile: MultipartFile)

    fun uploadTests(multiPartFile: MultipartFile)
}