package com.sct.musinsa.assignment.common.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.io.InputStream
import org.springframework.core.io.ClassPathResource

class JsonReader {
    val objectMapper = jacksonObjectMapper()

    inline fun <reified T> readFromJson(resourcePath: String): T {
        val resource = ClassPathResource(resourcePath)
        val inputStream: InputStream = resource.inputStream
        return objectMapper.readValue(inputStream, object : TypeReference<T>() {})
    }
}