package com.kakao.assignment.settlement.adaptor.`in`.rest.dto

import java.time.Instant

data class CreateSettlementRequest (
    val requestUserList: List<Long>,
    val totalAmount: Int,
    val alarmTime: Instant
)
