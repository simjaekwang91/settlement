package com.kakao.assignment.settlement.adaptor.`in`.rest.dto

data class ApiResponse<T>(
    val data: T?,
    val resultCode: ResultCode,
    val message: String? = null,
)

enum class ResultCode(
    val code: Int
) {
    Fail(-1),
    Success(0),
}
