package com.kakao.assignment.settlement.application.port.out.dto

import com.kakao.assignment.settlement.domain.SettlementDetailInfoDomainModel
import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import java.time.Instant

data class SettlementDetailInfoDto(
    val seq : Long = 0,
    val userInfo: UserInfoDto,
    val settlementInfo: SettlementInfoDto,
    val settlementDetailInfoTotalAmount: Int = 0,
    var settlementDetailInfoRemainAmount: Int = 0,
    var settlementDetailInfoStatus: SettlementStatus = SettlementStatus.Start,
    val createDate: Instant = Instant.now(),
    val updateDate: Instant = Instant.now(),
    val version:Long,
) {

    companion object {
        fun toDtoByDomain(settlementDetailInfoDomainModel: SettlementDetailInfoDomainModel): SettlementDetailInfoDto {
            return SettlementDetailInfoDto(
                settlementDetailInfoDomainModel.seq,
                UserInfoDto.toDtoByDomain(settlementDetailInfoDomainModel.userInfo),
                SettlementInfoDto.toDtoByDomain(settlementDetailInfoDomainModel.settlementInfo),
                settlementDetailInfoDomainModel.settlementDetailInfoTotalAmount,
                settlementDetailInfoDomainModel.settlementDetailInfoRemainAmount,
                settlementDetailInfoDomainModel.settlementDetailInfoStatus,
                settlementDetailInfoDomainModel.createDate,
                settlementDetailInfoDomainModel.updateDate,
                settlementDetailInfoDomainModel.version
            )
        }

        fun toDomainModel(settlementDetailInfoDto: SettlementDetailInfoDto): SettlementDetailInfoDomainModel {
            return SettlementDetailInfoDomainModel(
                settlementDetailInfoDto.seq,
                UserInfoDto.toDomainModel(settlementDetailInfoDto.userInfo),
                SettlementInfoDto.toDtoByDomain(settlementDetailInfoDto.settlementInfo),
                settlementDetailInfoDto.settlementDetailInfoTotalAmount,
                settlementDetailInfoDto.settlementDetailInfoRemainAmount,
                settlementDetailInfoDto.settlementDetailInfoStatus,
                settlementDetailInfoDto.createDate,
                settlementDetailInfoDto.updateDate,
                settlementDetailInfoDto.version
            )
        }
    }

    fun toDomainModel(): SettlementDetailInfoDomainModel {
        return SettlementDetailInfoDomainModel(
            seq,
            UserInfoDto.toDomainModel(userInfo),
            SettlementInfoDto.toDtoByDomain(settlementInfo),
            settlementDetailInfoTotalAmount,
            settlementDetailInfoRemainAmount,
            settlementDetailInfoStatus,
            createDate,
            updateDate,
            version
        )
    }
}

