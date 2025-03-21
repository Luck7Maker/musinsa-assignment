package com.sct.musinsa.assignment.common.http.validation

import com.sct.musinsa.assignment.common.http.exception.ConstraintViolationWrappedException
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.springframework.http.HttpStatus

/**
 * Validation check util(공통)
 * 비지니르 로직 구현중 validation을 체크 하고 싶을 경우 아래의 유틸을 사용
 */
class ValidationWrapper {
    companion object {
        private fun <T> wrappedValidate(obj: T): Set<ConstraintViolation<Any>> {
            val validator: Validator = Validation.buildDefaultValidatorFactory().validator
            return validator.validate(obj)
        }

        /**
         * Validation 오류의 정보를 제공
         */
        fun <T> validate(obj: T): List<ValidationsDescriber> {
            val validateResults: Set<ConstraintViolation<Any>> = wrappedValidate(obj)
            return validateResults.stream().map {
                ValidationsDescriber(fieldName = it.propertyPath.toString(), message = it.message)
            }.toList()
        }

        /**
         * Validation 오류에 발생시 Exception throw
         */
        fun <T> validateWithThrow(obj: T, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR) {
            val validateResults: Set<ConstraintViolation<Any>> = wrappedValidate(obj)
            if (validateResults.isNotEmpty()) {
                val validationDescriver = ValidationsDescriber(fieldName = validateResults.first().propertyPath.toString(), message = validateResults.first().message)
                throw ConstraintViolationWrappedException(validationDescriver, httpStatus)
            }
        }

        /**
         * ConstraintViolationException을 -> ConstraintViolationWrappedException converting
         */
        fun from(e: ConstraintViolationException, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ConstraintViolationWrappedException? {
            val validationDescriber = ValidationsDescriber(fieldName = e.constraintViolations.first().propertyPath.toString(), message = e.constraintViolations.first().message)
            return ConstraintViolationWrappedException(validationDescriber, httpStatus)
        }
    }
}
