package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementDetailInfo
import java.time.Instant

interface SettlementDetailInfoRepositoryCustom {
    fun findByKey(seq: Long): SettlementDetailInfo?
    fun findAllByUserId(userId: Long) : List<SettlementDetailInfo>
    fun findAllNotDone(seq: Long, status: Int): List<SettlementDetailInfo>
    fun findAllNeedToAlarm(settlementStatus: Int, findTime:Instant, alarmStatus: Int, limit: Long): List<SettlementDetailInfo>
}
