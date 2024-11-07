package org.prize.healthapp.application.service

import org.apache.commons.csv.CSVFormat
import org.prize.healthapp.adapter.out.s3.FileUploadResponseDto
import org.prize.healthapp.application.port.`in`.FileCommand
import org.prize.healthapp.application.port.out.PersonQuery
import org.prize.healthapp.application.port.out.PersonSummaryQuery
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.application.port.out.TestResultQuery
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.prize.healthapp.domain.person.Person
import org.prize.healthapp.domain.testresult.TestResult
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@Service
class FileService(
    private val logger: Logger,
    private val personQuery: PersonQuery,
    private val personSummaryQuery: PersonSummaryQuery,
    private val testResultQuery: TestResultQuery,
    private val s3Query: S3Query,
) : FileCommand {
    override fun uploadPersons(multiPartFile: MultipartFile): FileUploadResponseDto {
        if (!checkIfFileIsCSV(multiPartFile)) {
            throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }
        val csvData = convertToMap(multiPartFile)
        val persons: List<Person> = Person.from(csvData)
        personQuery.save(persons)
        val responseDto = s3Query.upload(multiPartFile)
        val personDistributionDto = Person.convert(persons)
        personSummaryQuery.distribute(personDistributionDto)
        return responseDto
    }

    override fun uploadTests(multiPartFile: MultipartFile): FileUploadResponseDto {
        if (!checkIfFileIsCSV(multiPartFile)) {
            throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }
        val csvData = convertToMap(multiPartFile)
        val tests = TestResult.from(csvData)
        val responseDto = s3Query.upload(multiPartFile)
        testResultQuery.save(tests)
        return responseDto
    }

    private fun checkIfFileIsCSV(file: MultipartFile): Boolean = file.contentType.equals("text/csv")

    private fun convertToMap(file: MultipartFile): List<Map<String, String>> {
        val csvData = mutableListOf<Map<String, String>>()
        try {
            file.inputStream.use { inputStream ->
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
        } catch (e: Exception) {
            logger.error("Error while parsing CSV file", e)
            throw BusinessException(ErrorCode.FILE_NOT_FOUND)
        }
        return csvData
    }
}
