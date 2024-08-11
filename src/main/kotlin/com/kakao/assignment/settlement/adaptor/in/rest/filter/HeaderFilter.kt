package com.kakao.assignment.settlement.adaptor.`in`.rest.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class HeaderFilter: GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        if (req.getHeader("X-USER-ID").isNullOrEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid header")
            return
        }

        chain?.doFilter(req, res)
    }
}
