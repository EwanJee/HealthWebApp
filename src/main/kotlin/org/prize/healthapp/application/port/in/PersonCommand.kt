@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.domain.person.Person

interface PersonCommand {
    fun createPersons(fileInfoDto: FileInfoDto): List<Person>
}
