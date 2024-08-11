package com.kakao.assignment.settlement.domain.enum

enum class AlarmStatus (
    val code:Int
) {
    Ready(0), //발송예정
    Done(1) // 발송완료
    ;

    companion object {
        fun fromCode(code: Int) = AlarmStatus.values().first { it.code == code }
    }

}

