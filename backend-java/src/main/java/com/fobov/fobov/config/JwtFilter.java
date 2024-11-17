package com.fobov.fobov.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Value("${jwt_secret}")
    private String JWT_SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if ("/usuarios/login".equals(path) || "/produtos".equals(path) ||
                "/clientes".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String auth = request.getHeader("Authorization");

            if (auth == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = auth.replace("Bearer ", "");

            SecretKey key =
                    Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
            boolean admin = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload()
                    .get("admin", Boolean.class);
//            boolean hasRoleAdmin =
//                    SecurityContextHolder.getContext().getAuthentication()
//                            .getAuthorities().stream().anyMatch(
//                                    grantedAuthority -> grantedAuthority
//                                    .getAuthority()
//                                            .equals("ADMIN"));
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            System.out.println(authentication.getCredentials());
            System.out.println(authentication.getAuthorities());
            if (admin) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}


