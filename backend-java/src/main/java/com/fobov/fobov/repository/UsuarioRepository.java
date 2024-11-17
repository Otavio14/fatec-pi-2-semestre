package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
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

    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM usuarios";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

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

    public Usuario findById(Integer id) {
        Usuario usuario = new Usuario();
        String sql = "SELECT id, nome, email FROM usuarios WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public boolean save(Usuario usuario) {
        String sqlCosultar =
                "SELECT id FROM usuarios WHERE email = ?" + "UNION" +
                        "SELECT id_clientes FROM clientes WHERE email = ?";

        String sqlInsert =
                "INSERT INTO usuarios (nome, email, senha) VALUES " + "(?, ?," +
                        " ?)";

        try {
            Connection connection = DATA_SOURCE.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlCosultar);
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            } else {
                preparedStatement.clearParameters();
                preparedStatement.clearBatch();
                preparedStatement.clearWarnings();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement(sqlInsert);
                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, usuario.getEmail());
                preparedStatement.setString(3, usuario.getSenha());

                preparedStatement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? " +
                "WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String login(Usuario usuario) {
        String sql = "SELECT id_clientes AS id, nome, email, senha, 0 AS " +
                "admin FROM" +
                " clientes WHERE email = ?" + " UNION " +
                "SELECT id, nome, email, senha, 1 AS admin FROM usuarios " +
                "WHERE email = ? LIMIT 1";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && passwordEncoder.matches(usuario.getSenha(),
                    resultSet.getString("senha"))) {
                SecretKey key =
                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));

                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.add(Calendar.HOUR, 20);

                return Jwts.builder().signWith(key)
                        .claim("id", resultSet.getInt("id"))
                        .claim("nome", resultSet.getString("nome"))
                        .claim("email", resultSet.getString("email"))
                        .claim("admin", resultSet.getInt("admin") == 1)
                        .expiration(calendar.getTime()).compact();
            } else {
                return "Dados inválidos!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Dados inválidos!";
    }
}
