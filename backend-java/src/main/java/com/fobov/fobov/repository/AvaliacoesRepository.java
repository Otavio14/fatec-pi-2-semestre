package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Avaliacao;
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
public class AvaliacoesRepository implements Crud<Avaliacao, Integer> {
    private final DataSource DATA_SOURCE;

    public AvaliacoesRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Avaliacao> findAll() {
        List<Avaliacao> avaliacaoList = new ArrayList<>();
        String sql = "SELECT avaliacoes.id, avaliacoes.nota, avaliacoes" +
                ".comentario, avaliacoes.dt_avaliacao, avaliacoes" +
                ".id_cliente, avaliacoes.id_produto, clientes.nome AS" +
                " cliente, produtos.nome AS produto FROM avaliacoes " +
                "LEFT JOIN clientes ON avaliacoes.id_cliente = " +
                "clientes.id LEFT JOIN produtos ON avaliacoes" +
                ".id_produto = produtos.id;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getInt("id"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getTimestamp("dt_avaliacao")
                        .toLocalDateTime());
                avaliacao.setIdCliente(resultSet.getInt("id_cliente"));
                avaliacao.setIdProduto(resultSet.getInt("id_produto"));
                avaliacao.setProduto(resultSet.getString("produto"));
                avaliacao.setCliente(resultSet.getString("cliente"));
                avaliacaoList.add(avaliacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacaoList;
    }

    public Avaliacao findById(Integer id) {
        Avaliacao avaliacao = null;
        String sql = "SELECT id, nota, comentario, dt_avaliacao, " +
                "id_cliente, id_produto FROM avaliacoes WHERE " + "id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getInt("id"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getTimestamp("dt_avaliacao")
                        .toLocalDateTime());
                avaliacao.setIdCliente(resultSet.getInt("id_cliente"));
                avaliacao.setIdProduto(resultSet.getInt("id_produto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacao;
    }

    public ResponseEntity<String> save(Avaliacao avaliacao) {
        String sql =
                "INSERT INTO avaliacoes (nota, comentario, dt_avaliacao, ) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, java.sql.Date.valueOf(
                    avaliacao.getDtAvaliacao().toLocalDate()));

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Avaliação cadastrada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Avaliacao avaliacao) {
        String sql =
                "UPDATE avaliacoes SET nota = ?, comentario = ?, dt_avaliacao" +
                        " = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, java.sql.Date.valueOf(
                    avaliacao.getDtAvaliacao().toLocalDate()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Avaliação alterada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM avaliacoes WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Avaliação excluída com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}