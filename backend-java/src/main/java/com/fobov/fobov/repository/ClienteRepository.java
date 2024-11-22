package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository implements Crud<Cliente, Integer> {
    private final DataSource DATA_SOURCE;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ClienteRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Cliente> findAll() {
        List<Cliente> clienteList = new ArrayList<>();
        String sql =
                "SELECT clientes.id, clientes.nome, clientes.cep, clientes" +
                        ".endereco, clientes.email, clientes.telefone, " +
                        "clientes.bairro, clientes.numero, clientes" +
                        ".id_cidade, estados.nome AS estado, cidades.nome AS " +
                        "cidade FROM clientes LEFT JOIN cidades ON cidades.id" +
                        " = clientes.id_cidade LEFT JOIN estados ON estados" +
                        ".id = cidades.id_estado;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCep(resultSet.getString("cep"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setIdCidade(resultSet.getInt("id_cidade"));
                cliente.setCidade(resultSet.getString("cidade"));
                cliente.setEstado(resultSet.getString("estado"));
                clienteList.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteList;
    }

    public Cliente findById(Integer id) {
        Cliente cliente = null;
        String sql = "SELECT id, nome, cep, endereco, email, " +
                "telefone, bairro, numero, id_cidade FROM clientes WHERE " +
                "id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCep(resultSet.getString("cep"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setIdCidade(resultSet.getInt("id_cidade"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public ResponseEntity<String> save(Cliente cliente) {
        String sqlConsultar =
                "SELECT id FROM usuarios WHERE email = ?" + " UNION " +
                        "SELECT id FROM clientes WHERE email = ?";

        String sqlInsert = cliente.getSenha() != null ?
                "INSERT INTO clientes (nome, cep, endereco, email, " +
                        "telefone, bairro, numero, id_cidade, senha) VALUES" +
                        " " + "(?, ?, ?, ?," + " ?, ?, ?, ?, ?)" :
                "INSERT INTO clientes (nome, cep, endereco, email, " +
                        "telefone, bairro, numero, id_cidade) VALUES" +
                        " (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatementConsultar =
                     connection.prepareStatement(
                     sqlConsultar)) {
            preparedStatementConsultar.setString(1, cliente.getEmail());
            preparedStatementConsultar.setString(2, cliente.getEmail());

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

                preparedStatementInsert.setString(1, cliente.getNome());
                preparedStatementInsert.setString(2, cliente.getCep());
                preparedStatementInsert.setString(3, cliente.getEndereco());
                preparedStatementInsert.setString(4, cliente.getEmail());
                preparedStatementInsert.setString(5, cliente.getTelefone());
                preparedStatementInsert.setString(6, cliente.getBairro());
                preparedStatementInsert.setString(7, cliente.getNumero());
                preparedStatementInsert.setInt(8, cliente.getIdCidade());

                if (cliente.getSenha() != null) {
                    cliente.setSenha(
                            passwordEncoder.encode(cliente.getSenha()));
                    preparedStatementInsert.setString(9, cliente.getSenha());
                }

                preparedStatementInsert.executeUpdate();
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Cliente cadastrado com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Cliente cliente) {
        String sqlConsultar =
                "SELECT id FROM usuarios WHERE email = ?" + " UNION " +
                        "SELECT id FROM clientes WHERE email = ? AND" +
                        " id != ?";

        String sqlUpdate = cliente.getSenha() != null ?
                "UPDATE clientes SET nome = ?, cep = ?, endereco = ?, " +
                        "email = ?, telefone = ?, bairro = ?, numero = ?, " +
                        "id_cidade = ?, senha = ?" + " " + "WHERE " + "id = ?" :
                "UPDATE clientes SET nome = ?, cep = ?, endereco = ?, " +
                        "email = ?, telefone = ?, bairro = ?, numero = ?, " +
                        "id_cidade = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatementConsultar =
                     connection.prepareStatement(
                     sqlConsultar)) {
            preparedStatementConsultar.setString(1, cliente.getEmail());
            preparedStatementConsultar.setString(2, cliente.getEmail());
            preparedStatementConsultar.setInt(3, id);

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

                preparedStatementUpdate.setString(1, cliente.getNome());
                preparedStatementUpdate.setString(2, cliente.getCep());
                preparedStatementUpdate.setString(3, cliente.getEndereco());
                preparedStatementUpdate.setString(4, cliente.getEmail());
                preparedStatementUpdate.setString(5, cliente.getTelefone());
                preparedStatementUpdate.setString(6, cliente.getBairro());
                preparedStatementUpdate.setString(7, cliente.getNumero());
                preparedStatementUpdate.setInt(8, cliente.getIdCidade());

                if (cliente.getSenha() != null) {
                    cliente.setSenha(
                            passwordEncoder.encode(cliente.getSenha()));
                    preparedStatementUpdate.setString(9, cliente.getSenha());
                    preparedStatementUpdate.setInt(10, id);
                } else {
                    preparedStatementUpdate.setInt(9, id);
                }

                preparedStatementUpdate.executeUpdate();
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Cliente alterado com sucesso!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}