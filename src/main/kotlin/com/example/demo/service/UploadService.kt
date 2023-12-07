package com.example.demo.service

import com.example.demo.dto.ApiException
import com.example.demo.dto.FleetData
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Service
class UploadService {


    fun uploadCsvFile(file: MultipartFile): List<FleetData> {
        throwIfFileEmpty(file)
        var fileReader: BufferedReader? = null
        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val csvToBean = createCSVToBean(fileReader)
            return csvToBean.parse()
        } catch (ex: Exception) {
            throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error during csv import")
        } finally {
            closeFileReader(fileReader)
        }
    }

    private fun throwIfFileEmpty(file: MultipartFile) {
        if (file.isEmpty)
            throw ApiException(400,"CSV file is empty.")
    }

    private fun createCSVToBean(fileReader: BufferedReader?): CsvToBean<FleetData> =
        CsvToBeanBuilder<FleetData>(fileReader)
            .withType(FleetData::class.java)
            .withSkipLines(1)
            .withIgnoreLeadingWhiteSpace(true)
            .build()

    private fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error during csv import")
        }
    }
}