package ru.chsergeig.shoppy.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
open class WebSecurity @Autowired constructor(
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    @param:Qualifier("jwtRequestFilter") private val jwtRequestFilter: OncePerRequestFilter,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Autowired
    fun configureGlobally(
        builder: AuthenticationManagerBuilder
    ) {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(
        httpSecurity: HttpSecurity
    ) {
        httpSecurity
            .cors().and()
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
            }
            .csrf().disable()
            .authorizeRequests().antMatchers("/admin/**").authenticated()
            .anyRequest().permitAll()
            .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    open fun passwordManager(): AuthenticationManager {
        return super.authenticationManager()
    }
}
