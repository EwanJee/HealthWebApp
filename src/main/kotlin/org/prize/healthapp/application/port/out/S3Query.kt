package org.prize.healthapp.application.port.out

import org.springframework.web.multipart.MultipartFile

interface S3Query {
    fun getCSV(fileName: String): List<Map<String, String>>

    fun upload(multiPartFile: MultipartFile)
}
