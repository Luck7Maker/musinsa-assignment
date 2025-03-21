package com.sct.musinsa.assignment.common.util

import java.security.SecureRandom
import java.util.*

object UuidGenerator {
    fun generateSecureRandomUuid(): String {
        val random = SecureRandom()
        val randomBytes = ByteArray(16) // UUID는 128비트 (16바이트)
        random.nextBytes(randomBytes)

        // UUID의 variant와 version 설정 (RFC 4122 표준 준수)
        randomBytes[6] = (randomBytes[6].toInt() and 0x0F or 0x40).toByte() // Version 4 설정
        randomBytes[8] = (randomBytes[8].toInt() and 0x3F or 0x80).toByte() // Variant 설정

        // 바이트 배열을 UUID 형식의 문자열로 변환
        return UUID.nameUUIDFromBytes(randomBytes).toString()
    }
}