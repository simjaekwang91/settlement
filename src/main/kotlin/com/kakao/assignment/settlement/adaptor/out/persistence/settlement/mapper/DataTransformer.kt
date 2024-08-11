package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.mapper

import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementDetailInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity.SettlementInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.UserInfo
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import com.kakao.assignment.settlement.domain.enum.AlarmStatus
import com.kakao.assignment.settlement.domain.enum.SettlementStatus

/*
APPLICATION LAYER의 Port를 통해 들어온 dto를 DB Entitiy로 변환
 */
class DataTransformer {
    companion object {
        fun SettlementInfoDto.toEntity() = SettlementInfo(
            this.seq,
            this.ownerUser.toEntity(),
            this.settlementTotalAmount, this.divideAmount, this.remainAmount,
            this.settlementUserCount,
            this.settlementStatus.code,
            this.alarmDate,
            this.createDate,
            this.updateDate,
            this.alarmStatus.code,
            this.version,

        )

        fun SettlementInfo.toDto() = SettlementInfoDto(
            this.seq,
            this.userInfo.toDto(),
            this.settlementTotalAmount,
            this.divideAmount,
            this.remainAmount,
            this.settlementUserCount,
            SettlementStatus.fromCode(this.settlementStatusCode),
            this.alarmDate,
            this.createDate,
            this.updateDate,
            this.version,
            AlarmStatus.fromCode(alarmStatusCode),
        )

        fun SettlementDetailInfo.toDto() = SettlementDetailInfoDto(
            this.seq,
            this.userInfo.toDto(),
            this.settlementInfo.toDto(),
            this.settlementDetailInfoTotalAmount,
            this.settlementDetailInfoRemainAmount,
            SettlementStatus.fromCode(this.settlementDetailInfoStatus),
            this.createDate,
            this.updateDate,
            this.version
        )


        fun SettlementDetailInfoDto.toEntity() = SettlementDetailInfo(
            this.seq,
            this.userInfo.toEntity(),
            this.settlementInfo.toEntity(),
            this.settlementDetailInfoTotalAmount,
            this.settlementDetailInfoRemainAmount,
            this.settlementDetailInfoStatus.code,
            this.createDate,
            this.updateDate,
            this.version
        )

        fun UserInfo.toDto() = UserInfoDto(
            this.userId, this.name, this.createDate, this.updateDate
        )

        fun UserInfoDto.toEntity() = UserInfo(
            this.userId, this.name, this.createDate, this.updateDate,
        )
    }

}

