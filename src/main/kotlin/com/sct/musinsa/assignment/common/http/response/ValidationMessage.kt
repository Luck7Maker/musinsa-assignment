package com.sct.musinsa.assignment.common.http.response

import com.sct.musinsa.assignment.common.http.validation.ValidationsDescriber


class ValidationMessage(override val code: String, override val message: String, val data: List<ValidationsDescriber>) :
    ResponseMessage
