package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.adapter

import com.kakao.assignment.settlement.adaptor.`in`.rest.logger.LogInfo
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.mapper.DataTransformer.Companion.toDto
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.mapper.DataTransformer.Companion.toEntity
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory.SettlementDetailInfoRepository
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.settlementinfo.reopsitory.SettlementInfoRepository
import com.kakao.assignment.settlement.adaptor.out.persistence.settlement.repository.userinfo.repository.UserInfoRepository
import com.kakao.assignment.settlement.application.port.out.SettlementPort
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SettlementPersistenceAdapter(
    private val settlementInfoRepository: SettlementInfoRepository,
    private val settlementDetailInfoRepository: SettlementDetailInfoRepository,
    private val userInfoRepository: UserInfoRepository,
    ) : SettlementPort {
    /**
     * 송금하기 실제 기능은 구현 하지 않음
     *
     * @param userId
     * @param settelmentid
     * @param amount
     * @return
     */
    override fun sendMoney(userId: Long, settelmentid: Long, amount: Int): Boolean {
        return true
    }

    /**
     * 정산 upsert 기능
     *
     * @param settlementInfoDto
     * @return
     */
    override fun setSettlement(settlementInfoDto: SettlementInfoDto): Long {
        try {
            return settlementInfoRepository.save(settlementInfoDto.toEntity()).seq
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * 정산 상세 upsert
     *
     * @param settlementDetailInfoDto
     * @return
     */
    override fun setSettlementDetail(settlementDetailInfoDto: SettlementDetailInfoDto): Boolean {
        try {
            settlementDetailInfoRepository.save(settlementDetailInfoDto.toEntity())
            return true
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * 정산 상세 내역을 일괄로 upsert 하기 위한 메서드
     *
     * @param settlementInfoDto
     * @return
     */
    override fun setSettlementDetailAll(settlementInfoDto: List<SettlementDetailInfoDto>): Boolean {
        try {
            settlementDetailInfoRepository.saveAll(settlementInfoDto.map { it.toEntity() })
            return true
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * 알람 발송 실제로 구현하지는 않음
     *
     * @param alarmList
     * @return
     */
    override fun sendAlarm(alarmList :List<SettlementDetailInfoDto>): Boolean {
        LogInfo.logger.info("알람 일괄 발송 성공")
        return true
    }

    /**
     * 정산 id로 정산 내역을 가져옴
     *
     * @param seq
     * @return
     */
    override fun getSettlementInfo(seq: Long): SettlementInfoDto {
        try {
            return settlementInfoRepository.findById(seq).get().toDto()
        } catch (e: Exception){
            throw e
        }

    }

    /**
     * 정산 완료 처리 메서드
     *
     * @param settlementInfoDto
     * @return
     */
    override fun setSettlementInfoDone(settlementInfoDto: SettlementInfoDto): Boolean {
        try {
            settlementInfoRepository.save(settlementInfoDto.toEntity())
            return true
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * 정산 상세 완료 처리 메서드
     *
     * @param settlementDetailInfoDto
     * @return
     */
    override fun setSettlementDetailInfoDone(settlementDetailInfoDto: SettlementDetailInfoDto): Boolean {
        try {
            settlementDetailInfoRepository.save(settlementDetailInfoDto.toEntity())
            return true
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * 정산 상세 내역을 id를 통해 조회
     *
     * @param seq
     * @return
     */
    override fun getSettlementDetailInfo(seq: Long): SettlementDetailInfoDto? {
        try {
            return settlementDetailInfoRepository.findByKey(seq)?.toDto()
        } catch (e: Exception){
            throw e
        }

    }

    /**
     * 정산 완료처리되지 않은 정산 상세 내역 조회
     *
     * @param seq
     * @param status
     * @return
     */
    override fun getSettlementDetailInfoNoneDone(seq: Long, status: Int): List<SettlementDetailInfoDto> {
        try {
            return settlementDetailInfoRepository.findAllNotDone(seq, status).map { it.toDto() }
        } catch (e: Exception){
            throw e
        }

    }

    /**
     * 내가 요청한 전체 정산내역 조회
     *
     * @param userId
     * @return
     */
    override fun getAllSettlementInfo(userId: Long): List<SettlementInfoDto> {
        try {
            return settlementInfoRepository.findAllByUserID(userId).map { it.toDto() }
        } catch (e: Exception){
            throw e
        }

    }

    /**
     * 내가 요청 받은 정산 내역 조회
     *
     * @param userId
     * @return
     */
    override fun getAllSettlementDetailInfo(userId: Long): List<SettlementDetailInfoDto> {
        try {
            return settlementDetailInfoRepository.findAllByUserId(userId).map {
                it.toDto()
            }
        } catch (e: Exception){
            throw e
        }
    }

    /**
     * user ID 를 통해 유저 정보 조회
     *
     * @param userId
     * @return
     */
    override fun getUserInfo(userId: Long): UserInfoDto {
        try {
            return userInfoRepository.findById(userId).get().toDto()
        }catch (e: Exception){
            throw e
        }
    }

    /**
     * 단건 조회를 피하기 위해 전체 유저를 한번에 조회
     *
     * @param userIdList
     * @return
     */
    override fun getAllUserInfo(userIdList: List<Long>): List<UserInfoDto> {
        try {
            return userInfoRepository.getUserInfos(userIdList).map {
                it.toDto()
            }
        } catch (e: Exception){
            throw e
        }
    }

    override fun getAlarmInfo(status: Int, findTime: Instant, alarmStatus: Int, limit:Long): List<SettlementDetailInfoDto>? {
        try {
            return settlementDetailInfoRepository.findAllNeedToAlarm(status, findTime, alarmStatus, limit).map { it.toDto() }
        }  catch (e: Exception){
            throw e
        }
    }
}
