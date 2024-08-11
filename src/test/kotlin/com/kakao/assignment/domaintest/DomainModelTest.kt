package com.kakao.assignment.domaintest

import com.kakao.assignment.settlement.domain.SettlementDetailInfoDomainModel
import com.kakao.assignment.settlement.domain.SettlementInfoDomainModel
import com.kakao.assignment.settlement.domain.UserInfoDomainModel
import com.kakao.assignment.settlement.domain.enum.SettlementStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainModelTest {
    @Test
    fun settlementInfoDomainModelInitTest(){
        val model = SettlementInfoDomainModel(ownerUser = UserInfoDomainModel(), settlementTotalAmount = 123488, settlementUserCount = 3, version = 0)
        assertEquals(41162, model.divideAmount)
        assertEquals(2, model.remainAmount)
    }

    @Test
    fun settlementDetailInfoDomainModelSetValueTest(){
        val model = SettlementDetailInfoDomainModel(userInfo = UserInfoDomainModel(), settlementInfo = SettlementInfoDomainModel(ownerUser = UserInfoDomainModel(), settlementTotalAmount = 123488, settlementUserCount = 3, version = 0), version = 0, settlementDetailInfoTotalAmount = 123488/3)
        model.setSettlementDetailDone()
        model.setRemainAmount(41062)
        assertEquals(SettlementStatus.Done, model.settlementDetailInfoStatus)
        assertEquals(100, model.settlementDetailInfoRemainAmount)
    }
}
