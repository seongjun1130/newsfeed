package com.sparta.newsfeedproject.domain.config.security;

import com.sparta.newsfeedproject.domain.filter.AuthFilter;
import com.sparta.newsfeedproject.domain.filter.AuthenticationExceptionHandlerFilter;
import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .formLogin((csrf) -> csrf.disable())
                .httpBasic((csrf) -> csrf.disable())
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/api/member/signup", "/api/member/login").permitAll()
                                .anyRequest().authenticated()
                );
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.addFilterBefore(authenticationExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter(jwtUtil);
    }

    @Bean
    public AuthenticationExceptionHandlerFilter authenticationExceptionHandlerFilter() {
        return new AuthenticationExceptionHandlerFilter();
    }
}
