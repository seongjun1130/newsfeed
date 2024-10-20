package com.sparta.newsfeedproject.domain.filter;

import com.sparta.newsfeedproject.domain.exception.UnAuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(0)
@Component
public class AuthenticationExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (UnAuthorizationException e) {
            if (!res.isCommitted()) {
                res.setStatus(e.getErrorCode().getStatus());
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write(e.getErrorCode().toString());
            }
        }
    }
}