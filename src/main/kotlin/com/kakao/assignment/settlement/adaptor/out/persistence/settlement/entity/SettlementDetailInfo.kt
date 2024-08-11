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
@Table(name = "settlement_detail_info", indexes = [
    Index(name = "idx_status", columnList = "status_code")
])
data class SettlementDetailInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_detail_seq")
    val seq : Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    val userInfo: UserInfo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_seq", foreignKey = ForeignKey(name = "fk_settlement_seq"))
    val settlementInfo: SettlementInfo,

    @Column(name = "settlement_detail_total_amount")
    val settlementDetailInfoTotalAmount: Int,

    @Column(name = "settlement_detail_remain_amount")
    var settlementDetailInfoRemainAmount: Int = 0,

    @Column(name = "status_code")
    val settlementDetailInfoStatus: Int,

    @Column(name = "create_date")
    val createDate: Instant,

    @Column(name = "update_date")
    val updateDate: Instant,

    //동시성 관리를 위한 version 설정
    @Version
    @Column(name = "version")
    val version: Long
)
