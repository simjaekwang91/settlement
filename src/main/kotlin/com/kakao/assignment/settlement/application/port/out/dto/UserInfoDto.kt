package com.kakao.assignment.settlement.application.port.out.dto

import com.kakao.assignment.settlement.domain.UserInfoDomainModel
import java.time.Instant

data class UserInfoDto (
    val userId: Long = 0,
    val name: String = "",
    val createDate: Instant = Instant.now(),
    val updateDate: Instant = Instant.now()
) {
    companion object{
        fun toDtoByDomain(userInfoDomainModel: UserInfoDomainModel) : UserInfoDto {
            return UserInfoDto(
                userInfoDomainModel.userId,
                userInfoDomainModel.name,
                userInfoDomainModel.createDate,
                userInfoDomainModel.updateDate
            )
        }

        fun toDomainModel(userInfoDto: UserInfoDto) : UserInfoDomainModel {
            return UserInfoDomainModel(
                userInfoDto.userId,
                userInfoDto.name,
                userInfoDto.createDate,
                userInfoDto.updateDate,
            )
        }
    }

    fun toDomainModel() : UserInfoDomainModel {
        return UserInfoDomainModel(
            userId,
            name,
            createDate,
            updateDate
        )
    }
}
