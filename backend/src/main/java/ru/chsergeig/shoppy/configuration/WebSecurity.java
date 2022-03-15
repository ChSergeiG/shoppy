package ru.chsergeig.shoppy.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.chsergeig.shoppy.component.JwtRequestFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @SneakyThrows
    @Autowired
    public void configureGlobally(
            AuthenticationManagerBuilder builder
    ) {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .authorizeRequests().antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @SneakyThrows
    @Bean
    public AuthenticationManager passwordManager() {
        return super.authenticationManager();
    }

}
