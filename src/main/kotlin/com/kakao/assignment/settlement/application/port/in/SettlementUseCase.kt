package com.kakao.assignment.settlement.application.port.`in`

import com.kakao.assignment.settlement.application.port.`in`.dto.CreateSettlementRequestDto
import com.kakao.assignment.settlement.application.port.`in`.dto.SendMoneyDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import java.time.Instant
import java.util.concurrent.CompletableFuture

/**
 * APPLICATION 레이어로 들어오기 위한 UseCase
 *
 * @constructor Create empty Settlement use case
 */
interface SettlementUseCase {
    /**
     * 송금하기
     *
     * @param userId
     * @param sendMoenyDto
     * @return
     */
    fun sendMoneyRequest(userId:Long, sendMoenyDto: SendMoneyDto): Boolean

    /**
     * 1:N 정산 요청 다건 생성
     *
     * @param ownerId
     * @param settlementList
     * @return
     */
    fun createSettlements(ownerId: Long, settlementList: List<CreateSettlementRequestDto>): Boolean

    /**
     * 내가 요청한 모든 정산 가져오기
     *
     * @param userId
     * @return
     */
    fun getTotalOwnerSettlement(userId: Long): List<SettlementInfoDto>

    /**
     * 내가 요청받은 모든 정산 가져오기
     *
     * @param userId
     * @return
     */
    fun getTotalRequestedSettlement(userId: Long): List<SettlementDetailInfoDto>

    /**
     * 알람 발송
     *
     * @param findTime
     * @return
     */
    fun sendAlarm(findTime: Instant): CompletableFuture<Boolean>
}
