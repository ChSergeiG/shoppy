package ru.chsergeig.shoppy

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@TestConfiguration
@Profile("test")
open class TestConfigurationContext {

    @Bean(name = ["jwtRequestFilter"])
    open fun jwtRequestFilter(): OncePerRequestFilter {
        return object : OncePerRequestFilter() {
            override fun doFilterInternal(
                request: HttpServletRequest,
                response: HttpServletResponse,
                filterChain: FilterChain
            ) {
                filterChain.doFilter(request, response)
            }
        }
    }

    open fun h2DataSource() {
    }
}
