package com.houseilla.houseillabackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain
    ) throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return; // Terminate execution line here so it doesn't trigger token authorization blocks
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        // 1. EXTRACT JWT SIGNATURE CONTEXT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(jwt);
            } catch (Exception e) {
                logger.error("Failed parsing or decoding incoming JSON Web Token: " + e.getMessage());
            }
        }

        // 2. INJECT PRINCIPAL IDENTITY TO SECURITY CONTEXT THREAD MAPS
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!jwtUtil.isTokenExpired(jwt)) {
                org.springframework.security.authentication.UsernamePasswordAuthenticationToken authenticationToken =
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                email, null, new java.util.ArrayList<>());

                authenticationToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));

                // Authorizes the transaction request execution securely
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
