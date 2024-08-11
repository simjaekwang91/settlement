package com.kakao.assignment.applicationtest

import com.kakao.assignment.settlement.application.port.`in`.dto.CreateSettlementRequestDto
import com.kakao.assignment.settlement.application.port.`in`.dto.SendMoneyDto
import com.kakao.assignment.settlement.application.port.out.SettlementPort
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import com.kakao.assignment.settlement.application.service.SettlementService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.Instant

@Transactional
class SettlementCommandServiceTest {
    private lateinit var settlementService: SettlementService
    private lateinit var settlementPort: SettlementPort

    private val user1 = UserInfoDto(1, "심심심", Instant.now(), Instant.now())
    private val user2 = UserInfoDto(2, "김김김", Instant.now(), Instant.now())
    private val user3 = UserInfoDto(3, "아아아", Instant.now(), Instant.now())

    @BeforeEach
    fun setUp() {
        // Mock 객체 생성
        settlementPort = mock(SettlementPort::class.java)
        `when`(settlementPort.getSettlementDetailInfo(1)).thenReturn(SettlementDetailInfoDto(userInfo = user2,settlementDetailInfoTotalAmount= 5000,
            settlementInfo= SettlementInfoDto(ownerUser =  UserInfoDto(), version = 0, settlementTotalAmount = 100000, settlementUserCount = 5), version = 0))
        `when`(settlementPort.sendMoney(1, 1, 5000)).thenReturn(true)
        `when`(settlementPort.setSettlement(SettlementInfoDto(
            ownerUser =  UserInfoDto(), version = 0
        ))).thenReturn(1)
        `when`(settlementPort.getAllUserInfo(listOf(1,2,3))).thenReturn(listOf(user1,user2,user3))
        `when`(settlementPort.getAllSettlementInfo(1)).thenReturn(listOf(SettlementInfoDto(
            ownerUser =  user1, version = 0
        )))
        `when`(settlementPort.getAllSettlementDetailInfo(2)).thenReturn(listOf(
            SettlementDetailInfoDto(
                userInfo =  user2,
                settlementInfo = SettlementInfoDto(ownerUser =  UserInfoDto(userId = 1), version = 0),
                version = 0
                )))
        `when`(settlementPort.getAllUserInfo(listOf(2,3,1))).thenReturn(listOf(user1, user2, user3))

        settlementService = SettlementService(settlementPort)
    }

    @Test
    fun sendMoneySuccessTest() {
        val result = settlementService.sendMoneyRequest(1, SendMoneyDto( 1, 1, 5000))
        assertTrue(result, "송금에 실패했습니다.")
    }

    @Test
    fun settlementRequestTest() {
        val createSettlementRequestDto = CreateSettlementRequestDto(listOf(2, 3))
        val result = settlementService.createSettlements(1, listOf(createSettlementRequestDto))
        assertTrue(result, "정산을 생성하지 못했습니다.")
    }

    @Test
    fun settlementRequestFailTest() {
        val createSettlementRequestDto = CreateSettlementRequestDto(listOf(2, 3))
        val result = settlementService.createSettlements(5, listOf(createSettlementRequestDto))

        assertFalse(result, "정산을 생성하지 못했습니다.")
    }

    @Test
    fun getAllSettlementInfoTest() {
        val createSettlementRequestDto = CreateSettlementRequestDto(listOf(2, 3))
        settlementService.createSettlements(1, listOf(createSettlementRequestDto))
        val result = settlementService.getTotalOwnerSettlement(1)

        assertTrue(result.isNotEmpty(), "조회된 정산이 없습니다.")
    }

    @Test
    fun getAllSettlementDetailInfoTest() {
        val createSettlementRequestDto = CreateSettlementRequestDto(listOf(2, 3))
        settlementService.createSettlements(1, listOf(createSettlementRequestDto))
        val result = settlementService.getTotalRequestedSettlement(2)

        assertTrue(result.isNotEmpty(), "조회된 정산이 없습니다.")
    }
}
