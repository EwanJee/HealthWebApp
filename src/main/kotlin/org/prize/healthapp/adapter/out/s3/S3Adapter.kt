@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.prize.healthapp.adapter.out.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import org.apache.commons.csv.CSVFormat
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.infrastructure.annotations.Adapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID

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

    override fun upload(multiPartFile: MultipartFile): FileUploadResponseDto {
        if (multiPartFile.isEmpty) throw BusinessException(ErrorCode.FILE_NOT_FOUND)
        multiPartFile.originalFilename ?: throw BusinessException(ErrorCode.WRONG_FILE_NAME)
        if (!multiPartFile.originalFilename!!.endsWith(".csv")) throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        checkIfFileHasValidFormat(multiPartFile)
        val fileName = createFileName(multiPartFile.originalFilename!!)
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = multiPartFile.contentType
        s3Client.putObject(
            PutObjectRequest(bucketName, fileName, multiPartFile.inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead),
        )
        return FileUploadResponseDto(s3Client.getUrl(bucketName, fileName).toString(), fileName)
    }

    private fun checkIfFileHasValidFormat(file: MultipartFile) {
        file.inputStream.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                val csvParser =
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(reader)
                if (csvParser.headerNames.isEmpty()) {
                    throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
                }
            }
        }
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

    private fun createFileName(fileName: String): String {
        val fileNameWithoutExtension = fileName.substringBeforeLast(".")
        val extension = fileName.substringAfterLast(".")
        val code = UUID.randomUUID().toString()
        return "$fileNameWithoutExtension-$code.$extension"
    }
}
