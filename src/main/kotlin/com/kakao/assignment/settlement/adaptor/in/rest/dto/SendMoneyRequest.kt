package com.kakao.assignment.settlement.adaptor.`in`.rest.dto

data class SendMoneyRequest(
    val settlementInfoSeq: Long,
    val settlementDetailSeq: Long,
    val amount: Int
)
