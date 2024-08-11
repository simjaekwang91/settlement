package com.kakao.assignment.settlement.adaptor.out.persistence.user.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant


@Entity
@Table(name = "kakaoUser")
data class UserInfo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId : Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "create_date")
    val createDate: Instant,

    @Column(name = "update_date")
    val updateDate: Instant,
)

