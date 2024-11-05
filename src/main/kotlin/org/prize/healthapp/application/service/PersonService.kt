package org.prize.healthapp.application.service

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.application.port.`in`.PersonCommand
import org.prize.healthapp.application.port.out.PersonQuery
import org.prize.healthapp.application.port.out.S3Query
import org.prize.healthapp.domain.person.Person
import org.springframework.stereotype.Service

@Service
class PersonService(
    private val personQuery: PersonQuery,
    private val s3Query: S3Query,
) : PersonCommand {
    override fun createPersons(fileInfoDto: FileInfoDto): List<Person> {
        val (fileName) = fileInfoDto
        val csvData: List<Map<String, String>> = s3Query.getCSV(fileName)
        val persons: List<Person> = Person.from(csvData)
        personQuery.save(persons)
        return persons
    }
}