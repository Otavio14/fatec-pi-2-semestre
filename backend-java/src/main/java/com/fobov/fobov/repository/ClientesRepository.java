package com.fobov.fobov.repository;

import com.fobov.fobov.model.Clientes;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class ClientesRepository {
    private final DataSource DATA_SOURCE;

    public ClientesRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Clientes> findAll() {
        List<Clientes> clientesList = new ArrayList<>();
        String sql = "SELECT id_clientes, nome, cep, endereco, email, telefone, bairro, numero, id_cidades FROM clientes";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Clientes cliente = new Clientes();
                cliente.setId(resultSet.getInt("id_clientes"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCep(resultSet.getString("cep"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setIdCidades(resultSet.getInt("id_cidades")); // Chave estrangeira
                clientesList.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientesList;
    }

    public Clientes findById(int id_clientes) {
        Clientes cliente = null;
        String sql = "SELECT id_clientes, nome, cep, endereco, email, telefone, bairro, numero, id_cidades FROM clientes WHERE id_clientes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_clientes);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cliente = new Clientes();
                cliente.setId(resultSet.getInt("id_clientes"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCep(resultSet.getString("cep"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setTelefone(resultSet.getString("telefone"));
                cliente.setBairro(resultSet.getString("bairro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setIdCidades(resultSet.getInt("id_cidades")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    public boolean save(Clientes cliente) {
        String sql = "INSERT INTO clientes (nome, cep, endereco, email, telefone, bairro, numero) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getCep());
            preparedStatement.setString(3, cliente.getEndereco());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setString(5, cliente.getTelefone());
            preparedStatement.setString(6, cliente.getBairro());
            preparedStatement.setString(7, cliente.getNumero());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_clientes, Clientes cliente) {
        String sql = "UPDATE clientes SET nome = ?, cep = ?, endereco = ?, email = ?, telefone = ?, bairro = ?, numero = ? WHERE id_clientes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getCep());
            preparedStatement.setString(3, cliente.getEndereco());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setString(5, cliente.getTelefone());
            preparedStatement.setString(6, cliente.getBairro());
            preparedStatement.setString(7, cliente.getNumero());
            preparedStatement.setInt(8, id_clientes); // ID do cliente a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return false;
    }

    public boolean delete(int id_clientes) {
        String sql = "DELETE FROM clientes WHERE id_clientes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_clientes);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}