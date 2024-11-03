package com.fobov.fobov.repository;

import com.fobov.fobov.model.Avaliacao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvaliacoesRepository {
    private final DataSource DATA_SOURCE;

    public AvaliacoesRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Avaliacao> findAll() {
        List<Avaliacao> avaliacaoList = new ArrayList<>();
        String sql = "SELECT id_avaliacoes, nota, comentario, dt_avaliacao, id_clientes, id_produtos FROM avaliacoes";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getInt("id_avaliacoes"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getTimestamp("dt_avaliacao").toLocalDateTime());
                avaliacao.setIdCliente(resultSet.getInt("id_clientes"));
                avaliacao.setIdProduto(resultSet.getInt("id_produtos"));
                avaliacaoList.add(avaliacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacaoList;
    }

    public Avaliacao findById(int id) {
        Avaliacao avaliacao = null;
        String sql = "SELECT id_avaliacoes, nota, comentario, dt_avaliacao, id_clientes, id_produtos FROM avaliacoes WHERE id_avaliacoes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avaliacao = new Avaliacao();
                avaliacao.setId(resultSet.getInt("id_avaliacoes"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getTimestamp("dt_avaliacao").toLocalDateTime());
                avaliacao.setIdCliente(resultSet.getInt("id_clientes"));
                avaliacao.setIdProduto(resultSet.getInt("id_produtos"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacao;
    }

    public boolean save(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacoes (nota, comentario, dt_avaliacao, ) VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, java.sql.Date.valueOf(avaliacao.getDtAvaliacao().toLocalDate()));

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_avaliacoes, Avaliacao avaliacao) {
        String sql = "UPDATE avaliacoes SET nota = ?, comentario = ?, dt_avaliacao = ? WHERE id_avaliacoes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, java.sql.Date.valueOf(avaliacao.getDtAvaliacao().toLocalDate()));
            preparedStatement.setInt(4, id_avaliacoes);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_avaliacoes) {
        String sql = "DELETE FROM avaliacoes WHERE id_avaliacoes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_avaliacoes);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}