package org.prize.healthapp.adapter.out.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.S3Object
import org.apache.commons.csv.CSVFormat
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.beans.factory.annotation.Value
import java.io.BufferedReader
import java.io.InputStreamReader

@Adapter
class S3Adapter(
    val s3Client: AmazonS3,
) : S3Query {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucketName: String

    override fun getCSV(fileName: String): List<Map<String, String>> {
        val s3Object: S3Object =
            s3Client.getObject(bucketName, fileName) ?: throw BusinessException(ErrorCode.FILE_NOT_FOUND)
        val csvData = mutableListOf<Map<String, String>>()
        s3Object.objectContent.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                val csvParser =
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(reader)
                for (record in csvParser) {
                    val row = record.toMap()
                    csvData.add(row)
                }
            }
        }
        return csvData
    }
}
