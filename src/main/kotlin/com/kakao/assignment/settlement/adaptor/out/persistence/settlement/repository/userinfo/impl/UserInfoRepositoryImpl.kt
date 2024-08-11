package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.userinfo.impl

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.userinfo.repository.UserInfoRepositoryCustom
import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.QUserInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.UserInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserInfoRepositoryImpl(private val queryFactory: JPAQueryFactory): UserInfoRepositoryCustom {
    override fun getUserInfos(userIdList: List<Long>): List<UserInfo> {
        val qUserInfo = QUserInfo.userInfo

        return queryFactory
            .selectFrom(qUserInfo)
            .where(qUserInfo.userId.`in` (userIdList))
            .fetch()
    }
}
