package org.prize.healthapp.application.service

import org.prize.healthapp.application.port.`in`.FileCommand
import org.prize.healthapp.domain.exception.BusinessException
import org.prize.healthapp.domain.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService : FileCommand {
    override fun uploadPersons(multiPartFile: MultipartFile) {
        if (!checkIfFileIsCSV(multiPartFile)) {
            throw BusinessException(ErrorCode.WRONG_FILE_FORMAT)
        }
    }

    override fun uploadTests(multiPartFile: MultipartFile) {
        TODO("Not yet implemented")
    }

    private fun checkIfFileIsCSV(file: MultipartFile): Boolean = file.contentType.equals("text/csv")
}
