package com.sct.musinsa.assignment.common.http.exception


import com.sct.musinsa.assignment.common.http.response.Response
import com.sct.musinsa.assignment.common.http.validation.ValidationsDescriber
import org.springframework.http.HttpStatus

class ConstraintViolationWrappedException(
    override val code: String,
    override val message: String,
    httpStatus: HttpStatus
) : GlobalHttpException(code = code, message = message, httpStatus = httpStatus) {
    constructor() : this(
        Response.ResponseCode.ERROR_INTERNAL.code,
        Response.ResponseCode.ERROR_INTERNAL.message,
        HttpStatus.INTERNAL_SERVER_ERROR
    )

    constructor(validationsDescriber: ValidationsDescriber, httpStatus: HttpStatus) : this(
        validationsDescriber.message,
        validationsDescriber.fieldName,
        httpStatus
    )
}
