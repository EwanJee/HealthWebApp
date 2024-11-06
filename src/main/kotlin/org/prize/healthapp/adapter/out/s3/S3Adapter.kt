package org.prize.healthapp.adapter.out.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.S3Object
import org.apache.commons.csv.CSVFormat
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile
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
            try {
                s3Client.getObject(bucketName, fileName)
            } catch (e: AmazonS3Exception) {
                if (e.errorCode == "NoSuchKey") {
                    throw BusinessException(ErrorCode.FILE_NOT_FOUND)
                } else {
                    throw e // 다른 S3 관련 예외는 그대로 전달
                }
            }
        return convertToMap(s3Object)
    }

    override fun upload(multiPartFile: MultipartFile) {
        TODO("Not yet implemented")
    }

    private fun convertToMap(s3Object: S3Object): List<Map<String, String>> {
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
