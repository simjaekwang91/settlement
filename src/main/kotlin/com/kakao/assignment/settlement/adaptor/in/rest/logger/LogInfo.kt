package com.kakao.assignment.settlement.adaptor.`in`.rest.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogInfo {
    // slf4j logger에 직접 접근하여 커스텀 로그를 만들 수 있도록 public으로 선언해둠
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
}
