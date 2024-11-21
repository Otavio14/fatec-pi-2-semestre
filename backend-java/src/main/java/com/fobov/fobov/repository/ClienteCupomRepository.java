package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ClienteCupom;
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
public class ClienteCupomRepository implements Crud<ClienteCupom, Integer> {
    private final DataSource DATA_SOURCE;

    public ClienteCupomRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ClienteCupom> findAll() {
        List<ClienteCupom> clienteCupomList = new ArrayList<>();
        String sql =
                "SELECT id, data_utilizacao, id_cliente, id_cupom FROM " +
                        "clientes_cupons";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ClienteCupom clienteCupom = new ClienteCupom();
                clienteCupom.setId(resultSet.getInt("id"));
                clienteCupom.setDataUtilizacao(
                        resultSet.getTimestamp("data_utilizacao")
                                .toLocalDateTime());
                clienteCupom.setIdCliente(resultSet.getInt("id_cliente"));
                clienteCupom.setIdCupom(resultSet.getInt("id_cupom"));
                clienteCupomList.add(clienteCupom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteCupomList;
    }

    public ClienteCupom findById(Integer id) {
        ClienteCupom clienteCupom = null;
        String sql =
                "SELECT id, data_utilizacao, id_cliente, id_cupom FROM " +
                        "clientes_cupons WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clienteCupom = new ClienteCupom();
                clienteCupom.setId(resultSet.getInt("id"));
                clienteCupom.setDataUtilizacao(
                        resultSet.getTimestamp("data_utilizacao")
                                .toLocalDateTime());
                clienteCupom.setIdCliente(resultSet.getInt("id_cliente"));
                clienteCupom.setIdCupom(resultSet.getInt("id_cupom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienteCupom;
    }

    public ResponseEntity<String> save(ClienteCupom clienteCupom) {
        String sql = "INSERT INTO clientes_cupons (data_utilizacao) VALUES (?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(
                    clienteCupom.getDataUtilizacao().toLocalDate()));

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente cupom cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id,
                                         ClienteCupom clienteCupom) {
        String sql =
                "UPDATE clientes_cupons SET data_utilizacao = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(
                    clienteCupom.getDataUtilizacao().toLocalDate()));
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente cupom alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM clientes_cupons WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cliente cupom excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}