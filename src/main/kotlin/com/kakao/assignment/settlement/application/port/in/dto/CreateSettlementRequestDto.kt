package com.kakao.assignment.settlement.application.port.`in`.dto

import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.CreateSettlementRequest
import java.time.Instant

data class CreateSettlementRequestDto (
    val requestUserList: List<Long> = listOf(),
    val totalAmount: Int = 0,
    val alarmTime: Instant = Instant.now()
) {
    companion object{
        fun toDto(createSettlementRequest: CreateSettlementRequest) : CreateSettlementRequestDto {
            return CreateSettlementRequestDto(
                createSettlementRequest.requestUserList,
                createSettlementRequest.totalAmount,
                createSettlementRequest.alarmTime
            )
        }
    }
}
