package com.fobov.fobov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Classe que inicia a aplicação
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FobovApplication {
    public static void main(String[] args) {
        SpringApplication.run(FobovApplication.class, args);
    }
}
