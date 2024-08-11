package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.userinfo.repository

import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.UserInfo

interface UserInfoRepositoryCustom {
    /**
     * id List를 인자로 받아서 한번에 조회
     *
     * @param userIdList
     * @return
     */
    fun getUserInfos(userIdList: List<Long>): List<UserInfo>
}
