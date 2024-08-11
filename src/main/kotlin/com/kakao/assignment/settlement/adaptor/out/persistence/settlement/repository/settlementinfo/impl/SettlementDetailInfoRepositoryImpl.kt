package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.impl

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.QSettlementDetailInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.QSettlementInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementDetailInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory.SettlementDetailInfoRepositoryCustom
import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.QUserInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class SettlementDetailInfoRepositoryImpl(private val queryFactory: JPAQueryFactory) : SettlementDetailInfoRepositoryCustom {
    override fun findByKey(seq: Long): SettlementDetailInfo? {
        val qSettlementDetailInfo = QSettlementDetailInfo.settlementDetailInfo
        val qUserInfo = QUserInfo.userInfo
        val qSettlementInfo = QSettlementInfo.settlementInfo

        return queryFactory.selectFrom(qSettlementDetailInfo)
            .join(qSettlementDetailInfo.userInfo, qUserInfo).fetchJoin()
            .join(qSettlementDetailInfo.settlementInfo, qSettlementInfo).fetchJoin()
            .where(qSettlementDetailInfo.seq.eq(seq))
            .fetchOne()

    }

    override fun findAllByUserId(userId: Long): List<SettlementDetailInfo> {
        val qSettlementDetailInfo = QSettlementDetailInfo.settlementDetailInfo
        val qUserInfo = QUserInfo.userInfo

        return queryFactory.selectFrom(qSettlementDetailInfo)
            .join(qSettlementDetailInfo.userInfo, qUserInfo).fetchJoin()
            .join(qSettlementDetailInfo.settlementInfo).fetchJoin()
            .where(qUserInfo.userId.eq(userId))
            .fetch()
    }

    override fun findAllNotDone(seq: Long, status: Int): List<SettlementDetailInfo> {
        val qSettlementDetailInfo = QSettlementDetailInfo.settlementDetailInfo
        val qSettlementInfo = QSettlementInfo.settlementInfo

        return queryFactory.selectFrom(qSettlementDetailInfo)
            .join(qSettlementDetailInfo.settlementInfo, qSettlementInfo).fetchJoin()
            .where(
                qSettlementDetailInfo.settlementDetailInfoStatus.ne(status),
                qSettlementInfo.seq.eq(seq)
            ).fetch()
    }

    override fun findAllNeedToAlarm(settlementStatus: Int, findTime: Instant, alarmStatus: Int, limit: Long): List<SettlementDetailInfo> {
        val qSettlementDetailInfo = QSettlementDetailInfo.settlementDetailInfo
        val qUserInfo = QUserInfo.userInfo

        return queryFactory.selectFrom(qSettlementDetailInfo)
            .limit(limit)
            .join(qSettlementDetailInfo.userInfo, qUserInfo).fetchJoin()
            .join(qSettlementDetailInfo.settlementInfo).fetchJoin()
            .where(
                qSettlementDetailInfo.settlementDetailInfoStatus.ne(settlementStatus),
                qSettlementDetailInfo.settlementInfo.alarmDate.before(findTime),
                qSettlementDetailInfo.settlementInfo.alarmStatusCode.eq(alarmStatus)
            ).fetch()
    }
}
