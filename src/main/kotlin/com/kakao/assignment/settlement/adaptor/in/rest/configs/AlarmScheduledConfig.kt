package com.kakao.assignment.settlement.adaptor.`in`.rest.configs

import com.kakao.assignment.settlement.adaptor.`in`.rest.logger.LogInfo
import com.kakao.assignment.settlement.application.port.`in`.SettlementUseCase
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.Instant

@Configuration
@EnableScheduling
class AlarmScheduledConfig(private val settlementUseCase: SettlementUseCase) {

    @Scheduled(cron = "0 * * * * ?")
    fun task(){
        try {
            settlementUseCase.sendAlarm(Instant.now())
                .thenAccept { result ->
                    LogInfo.logger.info("Alarm 발송 성공: $result")
                }
                .exceptionally { exception ->
                    LogInfo.logger.error("알람 발송 에러: ${exception.message}")
                    null
                }
        } catch (e: Exception){
            LogInfo.logger.error(e.toString())
        }
    }
}
