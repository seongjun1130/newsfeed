package com.sparta.newsfeedproject.domain.filter;

import com.sparta.newsfeedproject.domain.exception.UnAuthorizationException;
import com.sparta.newsfeedproject.domain.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode.INVALID_TOKEN;
import static com.sparta.newsfeedproject.domain.exception.eunm.ErrorCode.TOKEN_NOT_FOUND;

@Slf4j(topic = "AuthFilter")
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (isFilterApplicable(req)) {
            chain.doFilter(req, res);
            return;
        }
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        if (!StringUtils.hasText(tokenValue)) {
            throw new UnAuthorizationException(TOKEN_NOT_FOUND);
        }
        String accessToken = jwtUtil.substringToken(tokenValue);
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnAuthorizationException(INVALID_TOKEN);
        }

        // JWT 로부터 인증 정보를 가져옴
        Authentication auth = jwtUtil.getAuthentication(accessToken);
        // 인증 정보를 설정
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }

    private boolean isFilterApplicable(HttpServletRequest req) {
        String path = req.getRequestURI();
        return path.startsWith("/api/member/signup") || path.startsWith("/api/member/login");
    }
}
