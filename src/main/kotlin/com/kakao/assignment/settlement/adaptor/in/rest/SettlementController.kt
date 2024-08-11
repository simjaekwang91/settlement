package com.kakao.assignment.settlement.adaptor.`in`.rest

import com.kakao.assignment.settlement.adaptor.`in`.rest.customannotation.CustomHeaderInfo
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.ApiResponse
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.CreateSettlementRequest
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.HeaderCollection
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.ResultCode
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.SendMoneyRequest
import com.kakao.assignment.settlement.adaptor.`in`.rest.logger.LogInfo
import com.kakao.assignment.settlement.application.port.`in`.SettlementUseCase
import com.kakao.assignment.settlement.application.port.`in`.dto.CreateSettlementRequestDto
import com.kakao.assignment.settlement.application.port.`in`.dto.SendMoneyDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/settlement")
class SettlementController(private val settlementCommandUseCase: SettlementUseCase) {
    /**
     * 정산 생성
     *
     * @param createSettlementRequest 1/n 정산 요청 리스트
     * @param headerCollection 헤더로 부터 전달받은 정보 (id등등)
     * @return
     */
    @PostMapping("requset/create-settlement")
    fun createSettlement(@RequestBody createSettlementRequest: List<CreateSettlementRequest>,
                                 @CustomHeaderInfo headerCollection: HeaderCollection): ApiResponse<Boolean> {
        return try {
            ApiResponse(
                settlementCommandUseCase.createSettlements(headerCollection.userId, createSettlementRequest.map { CreateSettlementRequestDto.toDto(it) }), ResultCode.Success
            )
        } catch (e: Exception){
            LogInfo.logger.error(e.toString())
            ApiResponse(
                false, ResultCode.Fail, e.toString()
            )
        }
    }

    /**
     * 1/n 요청받은 정산금 송금
     *
     * @param sendMoneyRequest 정산 정보
     * @param headerCollection 헤더로 부터 전달받은 정보 (id등등)
     * @return
     */
    @PostMapping("request/sendmoney")
    fun sendMoney(@RequestBody sendMoneyRequest: SendMoneyRequest,  @CustomHeaderInfo headerCollection: HeaderCollection): ApiResponse<Boolean> {
        return try {
            ApiResponse(
                settlementCommandUseCase.sendMoneyRequest(headerCollection.userId, SendMoneyDto.getDto(sendMoneyRequest)), ResultCode.Success
            )
        } catch (e: Exception){
            LogInfo.logger.error(e.toString())
            ApiResponse(
                false, ResultCode.Fail, e.toString()
            )
        }
    }

    /**
     * 정산 요청한 모든 정산정보
     *
     * @param headerCollection 헤더로 부터 전달받은 정보 (id등등)
     * @return
     */
    @GetMapping("settlement-list/owner-list")
    fun getOwnerSettlementList(@CustomHeaderInfo headerCollection: HeaderCollection) : ApiResponse<List<SettlementInfoDto>> {
        return try {
            LogInfo.logger.debug("hello")
            ApiResponse(
                settlementCommandUseCase.getTotalOwnerSettlement(headerCollection.userId), ResultCode.Success
            )
        } catch (e: Exception){
            LogInfo.logger.error(e.toString())
            ApiResponse(
                null, ResultCode.Fail, e.toString()
            )
        }
    }

    /**
     * 정산 요청 받은 모든 정산정보
     *
     * @param headerCollection 헤더로 부터 전달받은 정보 (id등등)
     * @return
     */
    @GetMapping("/settlement-list/requested-list")
    fun getRequestedSettlementList(@CustomHeaderInfo headerCollection: HeaderCollection): ApiResponse<List<SettlementDetailInfoDto>>{
        return try {
            ApiResponse(
                settlementCommandUseCase.getTotalRequestedSettlement(headerCollection.userId), ResultCode.Success
            )
        } catch (e: Exception){
            LogInfo.logger.error(e.toString())
            ApiResponse(
                null, ResultCode.Fail, e.toString()
            )
        }
    }
}
