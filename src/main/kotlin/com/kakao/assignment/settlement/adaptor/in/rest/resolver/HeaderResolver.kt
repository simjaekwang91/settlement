package com.kakao.assignment.settlement.adaptor.`in`.rest.resolver

import com.kakao.assignment.settlement.adaptor.`in`.rest.customannotation.CustomHeaderInfo
import com.kakao.assignment.settlement.adaptor.`in`.rest.dto.HeaderCollection
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class HeaderResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CustomHeaderInfo::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest
        return HeaderCollection(request.getHeader("X-USER-ID").toLong())
    }
}
