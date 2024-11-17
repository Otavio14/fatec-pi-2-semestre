package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ClienteRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Cliente> findAll() {
        List<Cliente> clienteList = new ArrayList<>();
        String sql = "SELECT id_clientes, nome, cep, endereco, email, " +
                "telefone, bairro, numero, id_cidade FROM clientes";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id_clientes"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCep(resultSet.getString("cep"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setIdCidade(resultSet.getInt("id_cidade"));
                clienteList.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteList;
    }

    public Cliente findById(Integer id) {
        Cliente cliente = null;
        String sql = "SELECT id_clientes, nome, cep, endereco, email, " +
                "telefone, bairro, numero, id_cidade FROM clientes WHERE " +
                "id_clientes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt("id_clientes"));
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
        String sqlCosultar =
                "SELECT id FROM usuarios WHERE email = ?" + " UNION " +
                        "SELECT id_clientes FROM clientes WHERE email = ?";

        String sqlInsert =
                "INSERT INTO clientes (nome, cep, endereco, email, " +
                        "telefone, bairro, numero) VALUES (?, ?, ?, ?, ?, ?, " +
                        "?)";

        try {
            Connection connection = DATA_SOURCE.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlCosultar);
            preparedStatement.setString(1, cliente.getEmail());
            preparedStatement.setString(2, cliente.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email já utilizado!");
            } else {
                preparedStatement.clearParameters();
                preparedStatement.clearBatch();
                preparedStatement.clearWarnings();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement(sqlInsert);
                preparedStatement.setString(1, cliente.getNome());
                preparedStatement.setString(2, cliente.getCep());
                preparedStatement.setString(3, cliente.getEndereco());
                preparedStatement.setString(4, cliente.getEmail());
                preparedStatement.setString(5, cliente.getTelefone());
                preparedStatement.setString(6, cliente.getBairro());
                preparedStatement.setString(7, cliente.getNumero());

                preparedStatement.executeUpdate();
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
        String sqlCosultar =
                "SELECT id FROM usuarios WHERE email = ?" + " UNION " +
                        "SELECT id_clientes FROM clientes WHERE email = ? AND" +
                        " id_clientes != ?";

        String sqlUpdate =
                "UPDATE clientes SET nome = ?, cep = ?, endereco = ?, " +
                        "email = ?, telefone = ?, bairro = ?, numero = ? " +
                        "WHERE " + "id_clientes = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sqlCosultar);
            preparedStatement.setString(1, cliente.getEmail());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setInt(3, cliente.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email já utilizado!");
            } else {
                preparedStatement.clearParameters();
                preparedStatement.clearBatch();
                preparedStatement.clearWarnings();
                preparedStatement.close();

                preparedStatement = connection.prepareStatement(sqlUpdate);
                preparedStatement.setString(1, cliente.getNome());
                preparedStatement.setString(2, cliente.getCep());
                preparedStatement.setString(3, cliente.getEndereco());
                preparedStatement.setString(4, cliente.getEmail());
                preparedStatement.setString(5, cliente.getTelefone());
                preparedStatement.setString(6, cliente.getBairro());
                preparedStatement.setString(7, cliente.getNumero());
                preparedStatement.setInt(8, id);

                preparedStatement.executeUpdate();
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
        String sql = "DELETE FROM clientes WHERE id_clientes = ?";

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