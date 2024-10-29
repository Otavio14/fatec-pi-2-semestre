package com.fobov.fobov.repository;

import com.fobov.fobov.model.ClientesCupons;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class ClientesCuponsRepository {
    private final DataSource DATA_SOURCE;

    public ClientesCuponsRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ClientesCupons> findAll() {
        List<ClientesCupons> clientesCuponsList = new ArrayList<>();
        String sql = "SELECT id, data_utilizacao, id_clientes, id_cupons FROM clientes_cupons";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ClientesCupons clientesCupons = new ClientesCupons();
                clientesCupons.setId(resultSet.getInt("id"));
                clientesCupons.setDataUtilizacao(resultSet.getDate("data_utilizacao")); // Usa java.sql.Date
                clientesCupons.setIdCidades(resultSet.getInt("id_clientes")); // Chave estrangeira
                clientesCupons.setIdCidades(resultSet.getInt("id_cupons")); // Chave estrangeira
                clientesCuponsList.add(clientesCupons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientesCuponsList;
    }

    public ClientesCupons findById(int id) {
        ClientesCupons clientesCupons = null;
        String sql = "SELECT id, data_utilizacao, id_clientes, id_cupons FROM clientes_cupons WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clientesCupons = new ClientesCupons();
                clientesCupons.setId(resultSet.getInt("id"));
                clientesCupons.setDataUtilizacao(resultSet.getDate("data_utilizacao")); // Usa java.sql.Date
                clientesCupons.setIdCidades(resultSet.getInt("id_clientes")); // Chave estrangeira
                clientesCupons.setIdCidades(resultSet.getInt("id_cupons")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientesCupons;
    }

    public boolean save(ClientesCupons clientesCupons) {
        String sql = "INSERT INTO clientes_cupons (data_utilizacao) VALUES (?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(clientesCupons.getDataUtilizacao().getTime())); // Converte java.util.Date para java.sql.Date

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id, ClientesCupons clientesCupons) {
        String sql = "UPDATE clientes_cupons SET data_utilizacao = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(clientesCupons.getDataUtilizacao().getTime())); // Converte java.util.Date para java.sql.Date
            preparedStatement.setInt(2, id); // ID do registro a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM clientes_cupons WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}