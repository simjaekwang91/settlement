package com.kakao.assignment.settlement.application.port.out

import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import java.time.Instant

/**
 * APPLICAION 레이어에서 외부로 나가기 위한 Port
 *
 * @constructor Create empty Settlement port
 */
interface SettlementPort {
    //송금하기
    fun sendMoney(userId: Long, settelmentid: Long, amount: Int): Boolean
    //정산 upsert
    fun setSettlement(settlementInfoDto: SettlementInfoDto): Long
    //정산 상세내역 upsert
    fun setSettlementDetail(settlementDetailInfoDto: SettlementDetailInfoDto): Boolean
    //정산 상세 내역 한번에 upsert
    fun setSettlementDetailAll(settlementInfoDto: List<SettlementDetailInfoDto>): Boolean
    //알람 보내기
    fun sendAlarm(alarmList : List<SettlementDetailInfoDto>): Boolean

    fun getSettlementInfo(seq: Long): SettlementInfoDto
    //정산정보 완료 처리
    fun setSettlementInfoDone(settlementInfoDto: SettlementInfoDto) : Boolean
    //정산정보 완료 처리
    fun setSettlementDetailInfoDone(settlementDetailInfoDto: SettlementDetailInfoDto) : Boolean
    //요청받은 가져오기
    fun getSettlementDetailInfo(seq: Long) : SettlementDetailInfoDto?
    //완료 처리 되지 않은 정산 내역 가져오기
    fun getSettlementDetailInfoNoneDone(seq: Long, status: Int) : List<SettlementDetailInfoDto>
    //모든 내가 요청한 정산정보 가져오기
    fun getAllSettlementInfo(userId: Long) : List<SettlementInfoDto>
    //모든 내가 요청받은 정산정보 가져오기
    fun getAllSettlementDetailInfo(userId: Long) : List<SettlementDetailInfoDto>
    //회원 정보 가져오기
    fun getUserInfo(userId: Long): UserInfoDto
    //전달된 모든 회원정보 가져오기
    fun getAllUserInfo(userIdList: List<Long>): List<UserInfoDto>
    //알람 정보 가져오기
    fun getAlarmInfo(status: Int, findTime: Instant, alarmStatus: Int, limit:Long): List<SettlementDetailInfoDto>?

}
