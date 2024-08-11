package com.kakao.assignment.settlement.application.port.`in`.dto

import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.SendMoneyRequest

data class SendMoneyDto(
    val settlementInfoSeq: Long,
    val settlementDetailSeq: Long,
    val amount: Int
) {
    companion object{
        fun getDto(sendMoneyRequest: SendMoneyRequest) : SendMoneyDto {
            return SendMoneyDto(
                sendMoneyRequest.settlementInfoSeq,
                sendMoneyRequest.settlementDetailSeq,
                sendMoneyRequest.amount
            )
        }
    }
}
