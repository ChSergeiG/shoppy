package ru.chsergeig.shoppy.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
open class HttpServletRequestProvider @Autowired constructor(
    private val httpServletRequest: HttpServletRequest
) {
    open fun getHttpServletRequest(): HttpServletRequest = httpServletRequest
}
