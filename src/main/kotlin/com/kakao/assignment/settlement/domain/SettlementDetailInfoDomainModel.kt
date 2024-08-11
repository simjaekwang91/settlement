package com.kakao.assignment.settlement.domain

import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import java.time.Instant

data class SettlementDetailInfoDomainModel(
    val seq : Long = 0,
    val userInfo: UserInfoDomainModel,
    val settlementInfo: SettlementInfoDomainModel,
    val settlementDetailInfoTotalAmount: Int = 0,
    var settlementDetailInfoRemainAmount: Int = 0,
    var settlementDetailInfoStatus: SettlementStatus = SettlementStatus.Start,
    val createDate: Instant = Instant.now(),
    val updateDate: Instant = Instant.now(),
    val version:Long
    ) {

    init {
        try {
            settlementDetailInfoRemainAmount = settlementDetailInfoTotalAmount
        } catch (e: NumberFormatException) {
            throw e
        }
    }

    fun setSettlementDetailProceeding() {
        this.settlementDetailInfoStatus = SettlementStatus.Proceeding
    }

    fun setSettlementDetailDone() {
        this.settlementDetailInfoStatus = SettlementStatus.Done
    }

    fun setRemainAmount(amount: Int) {
        this.settlementDetailInfoRemainAmount = settlementDetailInfoRemainAmount - amount
    }
}

