package com.fobov.fobov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

/**
 * Classe destinada a inicializar o banco de dados, utilizando dos arquivos
 * create-tables.sql e insert-values.sql, que contém respectivamente os SQL's
 * de criação das tabelas e valores padrões dessas tabelas
 */
@Configuration
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    Environment env;
    @Autowired
    private DataSource dataSource;

    /**
     * Método que inicia o processo de execução dos arquivos de tabelas e
     * inserts
     *
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String url = Optional.ofNullable(env.getProperty("url")).orElse("");
        String dbFilePath = !url.isEmpty() ? url :
                "src/main/resources/database/database.db";
        File dbFile = new File(dbFilePath);

        if (dbFile.exists())
            dbFile.delete();

        try (Connection connection = dataSource.getConnection()) {
            executeSqlFile(connection, "/database/create-tables.sql");
            executeSqlFile(connection, "/database/insert-values.sql");
        }
    }

    /**
     * Executa um arquivo em SQL
     *
     * @param connection - conexão do banco de dados
     * @param filePath   - caminho do arquivo a ser executado
     */
    private void executeSqlFile(Connection connection, String filePath) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(filePath),
                        StandardCharsets.UTF_8))) {
            StringBuilder sql = new StringBuilder();
            String line;
            boolean inMultiLineComment = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("--")) {
                    continue; // Ignore single-line comments
                }
                if (line.startsWith("/*")) {
                    inMultiLineComment = true;
                }
                if (inMultiLineComment) {
                    if (line.endsWith("*/")) {
                        inMultiLineComment = false;
                    }
                    continue; // Ignore lines within multi-line comments
                }
                if (!line.isEmpty()) {
                    sql.append(line).append("\n");
                }
            }

            String[] statements = sql.toString().split(";");
            try (Statement statement = connection.createStatement()) {
                for (String stmt : statements) {
                    if (!stmt.trim().isEmpty()) {
                        statement.execute(stmt.trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
