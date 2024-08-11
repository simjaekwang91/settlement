package com.kakao.assignment.settlement.adaptor.`in`.rest.configs

import com.kakao.assignment.settlement.adaptor.`in`.rest.filter.HeaderFilter
import com.kakao.assignment.settlement.adaptor.`in`.rest.resolver.HeaderResolver
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig(private val headerFilter: HeaderFilter, private val headerResolver: HeaderResolver) : WebMvcConfigurer {
    @Bean
    fun headerFilterRegisteraionBean() :FilterRegistrationBean<Filter> {
        val registrationBean = FilterRegistrationBean<Filter>()
        registrationBean.filter = headerFilter
        registrationBean.addUrlPatterns("/*")
        return registrationBean
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(headerResolver)
    }
}
