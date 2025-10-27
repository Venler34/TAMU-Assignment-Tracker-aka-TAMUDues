package com.venler42.tamu_dues_api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/* Filter incoming requests to check for valid JWT and setting authentication context if valid */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JWTUtil jwtUtils;

    public JwtAuthenticationFilter(JWTUtil jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = parseJwt(request);
        if (token != null && jwtUtils.validateToken(token)) {
            UsernamePasswordAuthenticationToken authentication = jwtUtils.getAuthentication(token);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response); // continue filter chain cause added in between for filter
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.resolveToken(request);
        return jwt;
    }
}
