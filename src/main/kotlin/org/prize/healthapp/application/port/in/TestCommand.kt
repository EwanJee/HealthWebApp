@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.domain.testresult.TestResult

interface TestCommand {
    fun createTests(fileInfoDto: FileInfoDto): List<TestResult>
}
