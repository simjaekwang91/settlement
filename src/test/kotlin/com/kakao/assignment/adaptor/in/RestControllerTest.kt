package com.kakao.assignment.adaptor.`in`

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kakao.assignment.settlement.adaptor.`in`.rest.SettlementController
import com.kakao.assignment.settlement.adaptor.`in`.rest.configs.WebConfig
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.ApiResponse
import com.kakao.assignment.settlement.adaptor.`in`.rest.filter.HeaderFilter
import com.kakao.assignment.settlement.adaptor.`in`.rest.resolver.HeaderResolver
import com.kakao.assignment.settlement.application.port.`in`.SettlementUseCase
import com.kakao.assignment.settlement.application.port.`in`.dto.CreateSettlementRequestDto
import com.kakao.assignment.settlement.application.port.`in`.dto.SendMoneyDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementDetailInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.SettlementInfoDto
import com.kakao.assignment.settlement.application.port.out.dto.UserInfoDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
import java.time.Instant

@WebMvcTest(SettlementController::class)
@Import(WebConfig::class)
class RestControllerTest() {
    private val user1 = UserInfoDto(1, "심심심", Instant.now(), Instant.now())
    private val user2 = UserInfoDto(2, "김김김", Instant.now(), Instant.now())
    @Mock
    private lateinit var settlementController: SettlementController;

    @MockBean
    private lateinit var settlementUseCase: SettlementUseCase

    @Autowired
    private lateinit var mockMvc:MockMvc

    @BeforeEach
    fun setUp() {
        `when`(settlementUseCase.sendMoneyRequest(1,SendMoneyDto( 11,1,5000))).thenReturn(true)
        `when`(settlementUseCase.createSettlements(1, listOf(CreateSettlementRequestDto(alarmTime = Instant.MAX)))).thenReturn(true)
        `when`(settlementUseCase.getTotalOwnerSettlement(1)).thenReturn(listOf(SettlementInfoDto(ownerUser = user1, version = 0)))
        `when`(settlementUseCase.getTotalRequestedSettlement(2)).thenReturn(listOf(
            SettlementDetailInfoDto(
                userInfo =  user2,
                settlementInfo = SettlementInfoDto(ownerUser = user1, version = 0),
                version = 0
            )))
        settlementController = SettlementController(settlementUseCase)
        mockMvc = MockMvcBuilders.standaloneSetup(settlementController)
            .setCustomArgumentResolvers(HeaderResolver())
            .addFilters<StandaloneMockMvcBuilder>(HeaderFilter())
            .build()
    }

    @Test
    fun createNoHeaderTest() {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/settlement/settlement-list/requested-list")
            .header("Custom", 2))
            .andExpect(MockMvcResultMatchers.status().isBadRequest).andReturn()
    }

    @Test
    fun createSettlementTest () {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        val createSettlementRequestDtoList = listOf(CreateSettlementRequestDto(alarmTime = Instant.MAX))
        val jsonRequest = objectMapper.writeValueAsString(createSettlementRequestDtoList)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/settlement/requset/create-settlement")
            .contentType(MediaType.APPLICATION_JSON).content(jsonRequest).header("X-USER-ID", 1))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val contentString = result.response.contentAsString
        val apiResponse = objectMapper.readValue(contentString, ApiResponse::class.java)
        assertEquals(true, apiResponse.data)
    }

    @Test
    fun sendMoneyTest () {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
        val sendMoneyDto = SendMoneyDto( 11,1,5000)
        val jsonRequest = objectMapper.writeValueAsString(sendMoneyDto)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/settlement/request/sendmoney")
            .contentType(MediaType.APPLICATION_JSON).content(jsonRequest).header("X-USER-ID", 1))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val contentString = result.response.contentAsString
        val apiResponse = objectMapper.readValue(contentString, ApiResponse::class.java)
        assertEquals(true, apiResponse.data)
    }

    @Test
    fun getOwnerSettlementListTest () {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/settlement/settlement-list/owner-list")
            .header("X-USER-ID", 1))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val contentString = result.response.contentAsString
        val apiResponse = objectMapper.readValue(contentString, ApiResponse::class.java)
        val ownerSettlementList = apiResponse.data as List<*>
        assertEquals(true, ownerSettlementList.isNotEmpty())
    }

    @Test
    fun getReqestedSettlementListTest () {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/settlement/settlement-list/requested-list")
            .header("X-USER-ID", 2))
            .andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val contentString = result.response.contentAsString
        val apiResponse = objectMapper.readValue(contentString, ApiResponse::class.java)
        val ownerSettlementList = apiResponse.data as List<*>
        assertEquals(true, ownerSettlementList.isNotEmpty())
    }
}
