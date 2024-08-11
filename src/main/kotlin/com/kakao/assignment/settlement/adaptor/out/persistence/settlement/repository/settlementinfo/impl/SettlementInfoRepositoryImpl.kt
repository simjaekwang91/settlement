package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.impl

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.QSettlementInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory.SettlementInfoRepositoryCustom
import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.QUserInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SettlementInfoRepositoryImpl(private val queryFactory: JPAQueryFactory): SettlementInfoRepositoryCustom {
    override fun findAllByUserID(userId: Long) : List<SettlementInfo> {
        val qSettlementInfo = QSettlementInfo.settlementInfo
        val qUserInfo = QUserInfo.userInfo

        return queryFactory.selectFrom(qSettlementInfo)
            .join(qSettlementInfo.userInfo, qUserInfo).fetchJoin()
            .where(qUserInfo.userId.eq(userId))
            .fetch()
    }
}
