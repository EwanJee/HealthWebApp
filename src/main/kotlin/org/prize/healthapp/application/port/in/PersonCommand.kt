@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.application.service.PersonDistributionDto

interface PersonCommand {
    fun createPersons(fileInfoDto: FileInfoDto): Int

    fun getPersonDistribution(): List<PersonDistributionDto>
}
