@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.application.service.TestResultAvgDto

interface TestCommand {
    fun createTests(fileInfoDto: FileInfoDto): Int

    fun getTestAverage(): List<TestResultAvgDto>
}
