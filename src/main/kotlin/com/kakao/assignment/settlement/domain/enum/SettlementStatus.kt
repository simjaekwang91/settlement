package com.kakao.assignment.settlement.domain.enum

enum class SettlementStatus (
    val code:Int
) {
    Start(0), //정산 준비
    Proceeding(1), // 정산 진행중
    Done(2) // 정산 완료
    ;

    companion object {
        fun fromCode(code: Int) = SettlementStatus.values().first { it.code == code }
    }

}
