package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementDetailInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SettlementDetailInfoRepository: JpaRepository<SettlementDetailInfo, Long>, SettlementDetailInfoRepositoryCustom {
}
