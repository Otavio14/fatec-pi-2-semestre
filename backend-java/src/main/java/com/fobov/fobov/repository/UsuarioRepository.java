package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Repository
public class UsuarioRepository implements Crud<Usuario, Integer> {
    private final DataSource DATA_SOURCE;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Value("${jwt_secret}")
    private String JWT_SECRET;


    public UsuarioRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM usuarios";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    public Usuario findById(Integer id) {
        Usuario usuario = new Usuario();
        String sql = "SELECT id, nome, email FROM usuarios WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Cadastrar um novo registro
     *
     * @param usuario - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> save(Usuario usuario) {
        String sqlConsultar =
                "SELECT id FROM usuarios WHERE email = ?" + " UNION " +
                        "SELECT id FROM clientes WHERE email = ?";

        String sqlInsert = usuario.getSenha() != null ?
                "INSERT INTO usuarios (nome, email, senha) VALUES " + "(?, ?," +
                        " ?)" :
                "INSERT INTO usuarios (nome, email) VALUES " + "(?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatementConsultar =
                     connection.prepareStatement(
                     sqlConsultar)) {
            preparedStatementConsultar.setString(1, usuario.getEmail());
            preparedStatementConsultar.setString(2, usuario.getEmail());

            try (ResultSet resultSet =
                         preparedStatementConsultar.executeQuery()) {
                if (resultSet.next()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Email já utilizado!");
                }
            }

            try (PreparedStatement preparedStatementInsert =
                         connection.prepareStatement(
                    sqlInsert)) {
                preparedStatementInsert.setString(1, usuario.getNome());
                preparedStatementInsert.setString(2, usuario.getEmail());

                if (usuario.getSenha() != null) {
                    usuario.setSenha(
                            passwordEncoder.encode(usuario.getSenha()));
                    preparedStatementInsert.setString(3, usuario.getSenha());
                }

                preparedStatementInsert.executeUpdate();
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Usuário cadastrado com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    /**
     * Alterar um registro
     *
     * @param id      - ID do registro
     * @param usuario - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> update(Integer id, Usuario usuario) {
        String sqlConsultar =
                "SELECT id FROM usuarios WHERE email = ? AND id != ?" +
                        " UNION " + "SELECT id FROM clientes WHERE email = ?";

        String sqlUpdate = usuario.getSenha() != null ?
                "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE id " +
                        "= ?" :
                "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatementConsultar =
                     connection.prepareStatement(
                     sqlConsultar)) {
            preparedStatementConsultar.setString(1, usuario.getEmail());
            preparedStatementConsultar.setInt(2, id);
            preparedStatementConsultar.setString(3, usuario.getEmail());

            try (ResultSet resultSet =
                         preparedStatementConsultar.executeQuery()) {
                if (resultSet.next()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Email já utilizado!");
                }
            }

            try (PreparedStatement preparedStatementUpdate =
                         connection.prepareStatement(
                    sqlUpdate)) {
                preparedStatementUpdate.setString(1, usuario.getNome());
                preparedStatementUpdate.setString(2, usuario.getEmail());

                if (usuario.getSenha() != null) {
                    usuario.setSenha(
                            passwordEncoder.encode(usuario.getSenha()));
                    preparedStatementUpdate.setString(3, usuario.getSenha());
                    preparedStatementUpdate.setInt(4, id);
                } else {
                    preparedStatementUpdate.setInt(3, id);
                }

                preparedStatementUpdate.executeUpdate();

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Usuário alterado com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuário excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }

    /**
     * Realizar login no sistema
     *
     * @param usuario - Dados do usuário
     * @return resposta da operacao
     */
    public ResponseEntity<String> login(Usuario usuario) {
        String sql =
                "SELECT id AS id, nome, email, senha, 0 AS " + "admin FROM" +
                        " clientes WHERE email = ?" + " UNION " +
                        "SELECT id, nome, email, senha, 1 AS admin FROM " +
                        "usuarios " + "WHERE email = ? LIMIT 1";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getEmail());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() &&
                        passwordEncoder.matches(usuario.getSenha(),
                                resultSet.getString("senha"))) {
                    SecretKey key = Keys.hmacShaKeyFor(
                            Decoders.BASE64.decode(JWT_SECRET));

                    Date now = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(now);
                    calendar.add(Calendar.HOUR, 20);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(Jwts.builder().signWith(key)
                                    .claim("id", resultSet.getInt("id"))
                                    .claim("nome", resultSet.getString("nome"))
                                    .claim("email",
                                            resultSet.getString("email"))
                                    .claim("admin",
                                            resultSet.getInt("admin") == 1)
                                    .expiration(calendar.getTime()).compact());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Dados inválidos!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Dados inválidos!");
    }
}
