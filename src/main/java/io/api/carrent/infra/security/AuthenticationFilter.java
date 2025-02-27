package io.api.carrent.infra.security;

import io.api.carrent.infra.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = getToken(authHeader);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extractEmail(token);
        boolean notAuthenticated = SecurityContextHolder.getContext().getAuthentication() == null;

        if (email != null && notAuthenticated) {
            UserDetails user = userDetailsService.loadUserByUsername(email);

            if (jwtService.isValid(token, user)) {
                updateContext(user, request);
            }

            filterChain.doFilter(request, response);
        }
    }

    private void updateContext(UserDetails user, HttpServletRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getToken(String authHeader) {
        final String bearerPrefix = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(bearerPrefix)) {
            return null;
        }

        return authHeader.replace(bearerPrefix, "");
    }
}
