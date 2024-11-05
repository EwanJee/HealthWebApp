package org.prize.healthapp.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.prize.healthapp.domain.testresult.MeasurementData
import org.prize.healthapp.domain.testresult.TestResult
import org.slf4j.Logger
import java.nio.file.Paths

class Parser(
    private val logger: Logger,
) {
    private fun getMeasurementByCategory(): List<TestResult> {
        val list = mutableListOf<TestResult>()
        runBlocking {
            val path = Paths.get("src/main/resources/static/data/measurement_by_category").toFile()
            val csvFiles = path.listFiles { file -> file.isFile && file.extension == "csv" } ?: arrayOf()

            val jobs =
                csvFiles.map { file ->
                    async(Dispatchers.IO) {
                        logger.info("Reading file: ${file.name}")
                        CSVParser
                            .parse(file, Charsets.UTF_8, CSVFormat.DEFAULT.withFirstRecordAsHeader())
                            .use { parser ->
                                for (record in parser) {
                                    val age = record.get("AGE_FLAG_NM").toInt()
                                    val sex = record.get("SEXDSTN_FLAG_CD")
                                    val data = MeasurementData.from(record)
                                    val test = TestResult.of(age = age, sex = sex, data = data)
                                    list.add(test)
                                }
                            }
                    }
                }
            jobs.awaitAll()
        }
        return list
    }
}
