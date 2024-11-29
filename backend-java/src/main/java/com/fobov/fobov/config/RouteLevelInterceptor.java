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

/**
 * Um interceptador de rotas que identifica o nível da rota desejada e se o
 * usuário que está realizando a requisição possui permissão para acessá-la
 */
@Component
public class RouteLevelInterceptor implements HandlerInterceptor {
    @Value("${jwt_secret}")
    private String JWT_SECRET;

    /**
     * Método que vai de fato intercecptar as requisições e decidir se libera
     * o acesso
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance
     *                 evaluation
     * @return se é permitido ou não
     * @throws Exception
     */
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

    /**
     * Pega o nível de permissão que a requisição possui
     *
     * @param request - requisição
     * @return nível de permissão
     */
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
