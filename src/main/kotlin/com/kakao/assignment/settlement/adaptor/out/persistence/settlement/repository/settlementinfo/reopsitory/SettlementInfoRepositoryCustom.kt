package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementInfo
import java.time.Instant


interface SettlementInfoRepositoryCustom {
    fun findAllByUserID(userId: Long) : List<SettlementInfo>
}
