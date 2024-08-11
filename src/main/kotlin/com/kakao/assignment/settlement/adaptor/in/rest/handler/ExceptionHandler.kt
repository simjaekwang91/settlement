package com.kakao.assignment.settlement.adaptor.`in`.rest.handler

import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.ApiResponse
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.ResultCode
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice




@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(RuntimeException::class)
    fun <T> exception(e: Exception): ApiResponse<T> {
        return ApiResponse(
            null as T,
            ResultCode.Fail,
            e.message
        )
    }
}
