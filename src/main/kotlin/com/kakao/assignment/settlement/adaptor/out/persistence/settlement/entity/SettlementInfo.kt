package com.kakao.assignment.settlement.adaptor.out.persistence.settlement.entity

import com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity.UserInfo
import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.Instant

@Entity
@Table(name = "settlement_info", indexes = [Index(name = "idx_alarm_status", columnList = "alarm_status")
])
data class SettlementInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "settlement_seq")
    val seq: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val userInfo: UserInfo,

    @Column(name = "settlement_total_amount")
    val settlementTotalAmount: Int,

    @Column(name = "divide_amount")
    val divideAmount: Int,

    @Column(name = "remain_amount")
    val remainAmount: Int,

    @Column(name = "settlement_user_count")
    val settlementUserCount: Int,

    @Column(name = "status_code")
    val settlementStatusCode: Int,

    @Column(name = "alarm_date")
    val alarmDate: Instant,

    @Column(name = "create_date")
    val createDate: Instant,

    @Column(name = "update_date")
    val updateDate: Instant,

    @Column(name = "alarm_status")
    val alarmStatusCode: Int,

    @Version
    @Column(name = "version")
    val version: Long

)
