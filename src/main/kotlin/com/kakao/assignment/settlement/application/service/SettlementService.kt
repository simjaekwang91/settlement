package com.kakao.assignment.settlement.application.service

import com.kakao.assignment.settlement.adaptor.`in`.rest.logger.LogInfo
import com.kakao.assignment.settlement.application.port.`in`.SettlementUseCase
import com.kakao.assignment.settlement.application.port.`in`.dto.CreateSettlementRequestDto
import com.kakao.assignment.settlement.application.port.`in`.dto.SendMoneyDto
import com.kakao.assignment.settlement.application.port.out.SettlementPort
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import com.kakao.assignment.settlement.domain.SettlementDetailInfoDomainModel
import com.kakao.assignment.settlement.domain.SettlementInfoDomainModel
import com.kakao.assignment.settlement.domain.enum.AlarmStatus
import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.concurrent.CompletableFuture

@Service
class SettlementService(
    private val settlementPort: SettlementPort,
) : SettlementUseCase {

    /**
     * 송금 처리
     *
     * @param userId 송금 처리할 회원 id
     * @param sendMoenyDto 송금 상세 내역(정산 요청한 seq, 정산 요청 받은 seq, 송금액)
     * @return
     */
    @Transactional
    override fun sendMoneyRequest(userId:Long, sendMoenyDto: SendMoneyDto): Boolean {
        try {
            //내가 요청받은 정산 조회
            val settlementDetailInfoDomainModel = settlementPort.getSettlementDetailInfo(sendMoenyDto.settlementDetailSeq)?.toDomainModel() ?: throw Exception("조회된 송금 정보가 없습니다.")
            require(settlementDetailInfoDomainModel.settlementDetailInfoRemainAmount > 0) { "정산이 완료 됐습니다." }
            require(settlementDetailInfoDomainModel.settlementDetailInfoRemainAmount >= sendMoenyDto.amount) { "입력하신 금액이 잔액보다 큽니다." }

            //송금이 완료 되면 상태 업데이트
            if(settlementPort.sendMoney(userId, sendMoenyDto.settlementDetailSeq, sendMoenyDto.amount)) {
                //잔액 처리
                settlementDetailInfoDomainModel.setRemainAmount(sendMoenyDto.amount)
                //외부 포트를 통해 DB 완료 처리
                settlementPort.setSettlementDetail(SettlementDetailInfoDto.toDtoByDomain(settlementDetailInfoDomainModel))

                //잔액이 없으면 완료 처리
                if(settlementDetailInfoDomainModel.settlementDetailInfoRemainAmount <= 0) settlementDetailInfoDomainModel.setSettlementDetailDone()
                //잔액이 남아 있으면 정산중 처리
                else settlementDetailInfoDomainModel.setSettlementDetailProceeding()

                //외부 포트를 통해 DB 완료 처리
                settlementPort.setSettlementDetailInfoDone(SettlementDetailInfoDto.toDtoByDomain(settlementDetailInfoDomainModel))

                //요청한 모든 정산이 완료 처리인지
                if(settlementPort.getSettlementDetailInfoNoneDone(settlementDetailInfoDomainModel.settlementInfo.seq, SettlementStatus.Done.code).isEmpty()){
                    //모든 정산 완료 처리
                    settlementDetailInfoDomainModel.settlementInfo.setSettlementDone()
                    settlementPort.setSettlementInfoDone(SettlementInfoDto.toDtoByDomain(settlementDetailInfoDomainModel.settlementInfo))
                }
            }
            return true;
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * 정산 생성 N 차수에 해당하는 정산을 한건씩 insert 하기 보다는 최대한 한번에 DB 접근 하는 방향으로 고려
     *
     * @param ownerId 정산 요청자 id
     * @param settlementList 정산 차수 리스트
     * @return
     */
    @Transactional
    override fun createSettlements(ownerId: Long, settlementList: List<CreateSettlementRequestDto>): Boolean {
        try {
            // 모든 사용자 ID collect 및 중복 제거
            val allUserIds = settlementList.flatMap { it.requestUserList + listOf(ownerId) }.distinct()
            // 조회가 필요함 모든 사용자 정보 조회
            val allUserInfos = settlementPort.getAllUserInfo(allUserIds)
            // 모든 정산 정보 생성 및 저장
            settlementList.forEach { settlementRequest ->
                val ownerUser = allUserInfos.find{ userinfo -> userinfo.userId == ownerId} ?: throw IllegalArgumentException("사용자 정보가 없습니다.")

                // 정산 정보 생성
                val settlementDomainModel = SettlementInfoDomainModel(
                    ownerUser = UserInfoDto.toDomainModel(ownerUser),
                    settlementTotalAmount = settlementRequest.totalAmount,
                    settlementUserCount = settlementRequest.requestUserList.count().plus(1),
                    version = 0
                )

                // 정산 내역 저장
                val settlementSeq = settlementPort.setSettlement(SettlementInfoDto.toDtoByDomain(settlementDomainModel))
                settlementDomainModel.setSettlementSeq(settlementSeq)

                // 정산 상세 내역 생성
                val settlementDetailInfos = settlementRequest.requestUserList.map { userId ->
                    val userInfo = allUserInfos.find{ userinfo -> userinfo.userId == userId} ?: throw IllegalArgumentException("사용자 정보가 없습니다.")
                    SettlementDetailInfoDomainModel(
                        userInfo = UserInfoDto.toDomainModel(userInfo),
                        settlementInfo = settlementDomainModel,
                        settlementDetailInfoTotalAmount = settlementDomainModel.divideAmount,
                        settlementDetailInfoRemainAmount = settlementDomainModel.divideAmount,
                        version = 0
                    )
                }

                // 정상 상세 내역 일괄 저장
                settlementPort.setSettlementDetailAll(settlementDetailInfos.map(SettlementDetailInfoDto::toDtoByDomain))
            }
            return true
        } catch (e: Exception){
            return false
        }
    }

    /**
     * 내가 요청한 모든 정산 리스트 조회
     *
     * @param userId 회원정보
     * @return
     */
    @Transactional(readOnly = true)
    override fun getTotalOwnerSettlement(userId: Long): List<SettlementInfoDto> {
        try {
            return settlementPort.getAllSettlementInfo(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * 내가 요청 받은 모든 정산들
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    override fun getTotalRequestedSettlement(userId: Long): List<SettlementDetailInfoDto> {
        try {
            return settlementPort.getAllSettlementDetailInfo(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * 알람 발송 처리
     * DB 부하를 줄이기 위해 500개씩 끊어서 처리
     * @param findTime
     * @return
     */
    @Async
    @Transactional
    override fun sendAlarm(findTime: Instant): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            try {
                var hasMoreData = true
                //500개씩 나눠서 처리 DB 부하를 줄이기 위해
                while (hasMoreData) {
                    val alarmSettlementList = settlementPort.getAlarmInfo(SettlementStatus.Done.code, findTime, AlarmStatus.Ready.code, 500)?.map { it.toDomainModel() }
                    //발송할 알람이 있는지 확인
                    if (!alarmSettlementList.isNullOrEmpty()) {
                        val sendResult = settlementPort.sendAlarm(alarmSettlementList.map { SettlementDetailInfoDto.toDtoByDomain(it) })
                        if (sendResult) {
                            //알람 발송 이후 발송한 raw들은 발송 완료 update
                            alarmSettlementList.forEach {
                                it.settlementInfo.setAlarmStatusDone()
                                settlementPort.setSettlement(SettlementInfoDto.toDtoByDomain(it.settlementInfo))
                            }
                        }
                    } else {
                        hasMoreData = false
                    }
                }

                true
            } catch (e: Exception) {
                LogInfo.logger.error("알람 발송 실패", e)
                false
            }
        }
    }
}
