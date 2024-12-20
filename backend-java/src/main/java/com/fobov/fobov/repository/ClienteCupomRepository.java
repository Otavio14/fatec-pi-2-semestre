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

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    public List<ClienteCupom> findAll() {
        List<ClienteCupom> clienteCupomList = new ArrayList<>();
        String sql = "SELECT id, data_utilizacao, id_cliente, id_cupom FROM " +
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

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    public ClienteCupom findById(Integer id) {
        ClienteCupom clienteCupom = null;
        String sql = "SELECT id, data_utilizacao, id_cliente, id_cupom FROM " +
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

    /**
     * Cadastrar um novo registro
     *
     * @param clienteCupom - Dados do registro
     * @return resposta da operacao
     */
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

    /**
     * Alterar um registro
     *
     * @param id           - ID do registro
     * @param clienteCupom - Dados do registro
     * @return resposta da operacao
     */
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

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
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

    /**
     * Listar todos os registros com um ID de cliente
     *
     * @param id - ID do cliente
     * @return lista de registros
     */
    public ResponseEntity<String> checkByClienteId(ClienteCupom clienteCupom,
                                                   Integer id) {
        String sqlConsultar =
                "SELECT (CASE WHEN (SELECT cupons.id FROM cupons WHERE cupons" +
                        ".nome = ?) IS NULL THEN 'Cupom inexistente!' WHEN " +
                        "(SELECT clientes_cupons.id FROM clientes_cupons LEFT" +
                        " JOIN cupons ON clientes_cupons.id_cupom = cupons.id" +
                        " WHERE clientes_cupons.id_cupom = cupons.id AND " +
                        "clientes_cupons.id_cliente = ? AND cupons.nome = ?) " +
                        "IS NOT NULL THEN 'Cupom já utilizado!' ELSE '' " +
                        "END) AS mensagem;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatementConsultar =
                     connection.prepareStatement(
                     sqlConsultar)) {
            preparedStatementConsultar.setString(1,
                    clienteCupom.getCupom().getNome());
            preparedStatementConsultar.setInt(2, id);
            preparedStatementConsultar.setString(3,
                    clienteCupom.getCupom().getNome());

            try (ResultSet resultSet =
                         preparedStatementConsultar.executeQuery()) {
                if (resultSet.next()) {
                    String mensagem = resultSet.getString("mensagem");
                    if (!mensagem.isEmpty())
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(mensagem);
                }
            }
            String consultarPorcentagem =
                    "SELECT porcentagem FROM cupons WHERE nome = ?";

            try (PreparedStatement preparedStatementPorcentagem =
                         connection.prepareStatement(
                    consultarPorcentagem)) {
                preparedStatementPorcentagem.setString(1,
                        clienteCupom.getCupom().getNome());

                try (ResultSet resultSet =
                             preparedStatementPorcentagem.executeQuery()) {
                    if (resultSet.next()) {
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(resultSet.getString("porcentagem"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível usar o cupom!");
    }

    /**
     * Usar um cupom em um pedido
     *
     * @param clienteCupom - Dados do cupom e do cliente
     * @param id           - ID do cliente
     * @return resposta da operacao
     */
    public ResponseEntity<String> saveByClienteId(ClienteCupom clienteCupom,
                                                  Integer id) {
        String sql =
                "INSERT INTO clientes_cupons (id_cliente, data_utilizacao, " +
                        "id_cupom) VALUES (?, DATETIME('now', 'localtime'), " +
                        "(SELECT id FROM cupons WHERE nome = ?));";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, clienteCupom.getCupom().getNome());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Cupom utilizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível usar o cupom!");
    }
}