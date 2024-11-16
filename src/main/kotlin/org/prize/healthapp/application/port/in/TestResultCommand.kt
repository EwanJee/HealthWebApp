@file:Suppress("ktlint:standard:package-name")

package org.prize.healthapp.application.port.`in`

import org.prize.healthapp.adapter.`in`.FileInfoDto
import org.prize.healthapp.adapter.`in`.MyTestResultRequestDto
import org.prize.healthapp.application.service.MyTestResultReponseDto
import org.prize.healthapp.application.service.TestResultAvgDto
import org.prize.healthapp.domain.member.Member

interface TestResultCommand {
    fun createTests(fileInfoDto: FileInfoDto): Int

    fun getTestAverage(): List<TestResultAvgDto>

    fun testMy(myTestResultRequestDto: MyTestResultRequestDto): MyTestResultReponseDto

    fun findMyTest(id: String): Member
}
