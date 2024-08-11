package com.kakao.assignment.settlement.application.port.out.dto

import com.kakao.assignment.settlement.domain.SettlementInfoDomainModel
import com.kakao.assignment.settlement.domain.enum.AlarmStatus
import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import java.time.Instant

data class SettlementInfoDto (
    val seq: Long = 0,
    val ownerUser: UserInfoDto,
    val settlementTotalAmount: Int = 0,
    var divideAmount: Int = 0,
    var remainAmount: Int = 0,
    val settlementUserCount: Int = 0,
    var settlementStatus: SettlementStatus = SettlementStatus.Start,
    val alarmDate: Instant = Instant.now(),
    val createDate: Instant = Instant.now(),
    val updateDate: Instant = Instant.now(),
    val version: Long,
    val alarmStatus: AlarmStatus = AlarmStatus.Ready
) {
    companion object {
        fun toDtoByDomain(settlementInfoDomainModel: SettlementInfoDomainModel): SettlementInfoDto {
            return SettlementInfoDto(
                settlementInfoDomainModel.seq,
                UserInfoDto.toDtoByDomain(settlementInfoDomainModel.ownerUser),
                settlementInfoDomainModel.settlementTotalAmount,
                settlementInfoDomainModel.divideAmount,
                settlementInfoDomainModel.remainAmount,
                settlementInfoDomainModel.settlementUserCount,
                settlementInfoDomainModel.settlementStatus,
                settlementInfoDomainModel.alarmDate,
                settlementInfoDomainModel.createDate,
                settlementInfoDomainModel.updateDate,
                settlementInfoDomainModel.version,
                settlementInfoDomainModel.alarmStatus
            )
        }

        fun toDtoByDomain(settlementInfoDto: SettlementInfoDto): SettlementInfoDomainModel {
            return SettlementInfoDomainModel(
                settlementInfoDto.seq,
                UserInfoDto.toDomainModel(settlementInfoDto.ownerUser),
                settlementInfoDto.settlementTotalAmount,
                settlementInfoDto.divideAmount,
                settlementInfoDto.remainAmount,
                settlementInfoDto.settlementUserCount,
                settlementInfoDto.settlementStatus,
                settlementInfoDto.alarmDate,
                settlementInfoDto.createDate,
                settlementInfoDto.updateDate,
                settlementInfoDto.version,
                settlementInfoDto.alarmStatus
            )
        }
    }
}
