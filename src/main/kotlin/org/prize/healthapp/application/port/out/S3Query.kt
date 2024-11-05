package org.prize.healthapp.application.port.out

interface S3Query {
    fun getCSV(fileName: String): List<Map<String, String>>
}
