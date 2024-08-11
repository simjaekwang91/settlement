package com.kakao.assignment.settlement.adaptor.`in`.rest.configs

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

/**
 * 비동기 작업시 쓰레드 풀 및 큐 사이즈 설정
 * 서버 스펙에 따라 조정 가능
 *
 * @constructor Create empty Async config
 */
@Configuration
@EnableAsync
class AsyncConfig : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 50
        executor.initialize()
        return executor
    }
}
