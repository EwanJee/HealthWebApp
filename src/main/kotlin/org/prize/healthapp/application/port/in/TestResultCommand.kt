@file:Suppress("ktlint:standard:no-wildcard-imports", "ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.service.MyTestResultResponseDto
import org.prize.healthapp.application.service.TestResultAvgDto
import java.util.*

interface TestResultCommand {
    fun createTests(fileInfoDto: FileInfoDto): Int

    fun getTestAverage(): List<TestResultAvgDto>

    fun testMy(myTestResultRequestDto: MyTestResultRequestDto): MyTestResultResponseDto

    fun getMy(id: UUID): MyTestResultResponseDto
}
