package com.kakao.assignment.settlement.domain

import java.time.Instant

data class UserInfoDomainModel(
    val userId: Long = 0, val name: String = "", val createDate: Instant = Instant.now(), val updateDate: Instant = Instant.now()
)
