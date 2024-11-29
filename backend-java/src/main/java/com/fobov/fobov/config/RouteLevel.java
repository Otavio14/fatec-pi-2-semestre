package com.fobov.fobov.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface utilizada para definir o nível de uma rota, sendo:
 * 0 - Para rotas públicas
 * 1 - Para rotas exclusivas de clientes autenticados
 * 2 - Para rotas exclusivas de usuários ADMIN
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteLevel {
    int value();
}
