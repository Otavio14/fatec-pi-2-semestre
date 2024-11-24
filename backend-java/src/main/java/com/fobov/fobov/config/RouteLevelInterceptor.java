package com.fobov.fobov.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;

@Component
public class RouteLevelInterceptor implements HandlerInterceptor {
    @Value("${jwt_secret}")
    private String JWT_SECRET;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RouteLevel routeLevel =
                    handlerMethod.getMethodAnnotation(RouteLevel.class);


            if (routeLevel != null) {
                int requiredLevel = routeLevel.value();
                int requestLevel = getRequestLevel(request);

                if (requestLevel < requiredLevel) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
        }
        return true;
    }

    private int getRequestLevel(HttpServletRequest request) {
        try {

            String auth = request.getHeader("Authorization");

            if (auth == null)
                return 0;

            String token = auth.replace("Bearer ", "");

            SecretKey key =
                    Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
            boolean admin = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload()
                    .get("admin", Boolean.class);
            return admin ? 2 : 1;
        } catch (Exception ex) {
            return 0;
        }
    }
}
