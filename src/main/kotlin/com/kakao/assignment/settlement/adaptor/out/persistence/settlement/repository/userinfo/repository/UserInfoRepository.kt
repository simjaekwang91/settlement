package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.userinfo.repository

import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInfoRepository: JpaRepository<UserInfo, Long>, UserInfoRepositoryCustom {

}
