package com.sct.musinsa.assignment.common.http.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.sct.musinsa.assignment.common.http.exception.GlobalException
import com.sct.musinsa.assignment.common.http.exception.GlobalHttpException
import com.sct.musinsa.assignment.common.http.validation.ValidationsDescriber
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T> constructor(var code: String, var message: String, var data: T? = null) {

    companion object {
        // 응답코드/ 메시지/ payload용 응답
        fun <T> of(code: String, message: String, data: T? = null, httpStatus: HttpStatus = HttpStatus.OK): ResponseEntity<Response<T>> =
            ResponseEntity(Response(code = code, message = message, data = data),httpStatus)

        fun <T> from(responseMessage: ResponseMessage, data: T? = null, httpStatus: HttpStatus = HttpStatus.OK): ResponseEntity<Response<T>> =
            ResponseEntity(Response(code = responseMessage.code, message = responseMessage.message, data = data),httpStatus)

        fun <T> okFrom(data: T? = null): ResponseEntity<Response<T>> =
            ResponseEntity.ok(Response(code = ResponseCode.OK.code, message = ResponseCode.OK.message, data = data))

        fun <T> ok(): ResponseEntity<Response<T>> =
            ResponseEntity.ok(Response(code = ResponseCode.OK.code, message = ResponseCode.OK.message))

        fun errorFrom(exception: Exception, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<Response<String>> =
            ResponseEntity(
                Response(
                code = ResponseCode.ERROR_INTERNAL.code,
                message = exception.message
                    ?: ResponseCode.ERROR_INTERNAL.message
            ),httpStatus)

        fun errorFrom(responseMessage: ResponseMessage, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<Response<String>> =
            ResponseEntity(Response(code = responseMessage.code, message = responseMessage.message), httpStatus)

        fun errorFrom(responseMessage: ValidationMessage, httpStatus: HttpStatus = HttpStatus.BAD_REQUEST): ResponseEntity<Response<List<ValidationsDescriber>>> =
            ResponseEntity(Response(code = responseMessage.code, message = responseMessage.message, data = responseMessage.data),httpStatus)

        fun errorFrom(exception: GlobalException, httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<Response<String>> =
            ResponseEntity(
                Response(
                code = exception.code,
                message = exception.message
                    ?: ResponseCode.ERROR_INTERNAL.message
            ),httpStatus)

        fun errorFrom(exception: GlobalHttpException): ResponseEntity<Response<String>> =
            ResponseEntity(
                Response(
                code = exception.code,
                message = exception.message
                    ?: ResponseCode.ERROR_INTERNAL.message
            ), exception.httpStatus)
    }

    enum class ResponseCode(override val code: String, override val message: String) :
        ResponseMessage {
        OK("code.ok.200", "성공"),
        ERROR_INTERNAL("error.internal.500", "예기치 못한 오류가 발생 했습니다."),
        ERROR_SERVLET_DESERIALIZED("error.servlet-deserialized.500", "응답 생성중 오류가 발생했습니다."),

        ERROR_INTERNAL_FROM_SERVER("error.internal.501", "연계 서버로 부터 오류 응답을 수신 했습니다."),
        BAD_REQUEST("validation.bad-request.400", "요청 정보를 확인 하세요."),
        NOT_FOUND("error.not-found.404", "존재하지 않는 경로의 요청 입니다."),

        NO_SUCH_ELEMENT("error.not-found.4041", "데이터가 존재 하지 않습니다.")
        ;

        companion object {
            fun findByCode(code: String?): ResponseCode? {
                return ResponseCode.values().firstOrNull {
                    code == it.code
                }
            }
        }
    }
}
