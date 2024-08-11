package com.kakao.assignment.settlement.domain

import com.kakao.assignment.settlement.domain.enum.AlarmStatus
import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import java.time.Instant

data class SettlementInfoDomainModel (
    var seq: Long = 0,
    val ownerUser: UserInfoDomainModel,
    val settlementTotalAmount: Int = 0,
    var divideAmount: Int = 0,
    var remainAmount: Int = 0,
    val settlementUserCount: Int = 0,
    var settlementStatus: SettlementStatus = SettlementStatus.Start,
    val alarmDate: Instant = Instant.now(),
    val createDate: Instant = Instant.now(),
    val updateDate: Instant = Instant.now(),
    val version:Long,
    var alarmStatus: AlarmStatus = AlarmStatus.Ready
) {
    init {
        try {
            if(settlementTotalAmount != 0 || settlementUserCount != 0) {
                remainAmount = (settlementTotalAmount % settlementUserCount)
                divideAmount = (settlementTotalAmount / settlementUserCount)
            }
        } catch (e: NumberFormatException) {
            throw e
        }
    }

    fun setSettlementSeq(seq: Long) {
        this.seq = seq
    }

    fun setSettlementDone() {
        try {
            this.settlementStatus = SettlementStatus.Done
        } catch (e: Exception) {
            throw e
        }
    }
    fun setAlarmStatusDone() {
        alarmStatus = AlarmStatus.Done
    }
}
