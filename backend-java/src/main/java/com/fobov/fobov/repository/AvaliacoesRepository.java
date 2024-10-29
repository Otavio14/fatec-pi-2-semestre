package com.fobov.fobov.repository;

import com.fobov.fobov.model.Avaliacoes;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class AvaliacoesRepository {
    private final DataSource DATA_SOURCE;

    public AvaliacoesRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Avaliacoes> findAll() {
        List<Avaliacoes> avaliacoesList = new ArrayList<>();
        String sql = "SELECT id_avaliacoes, nota, comentario, dt_avaliacao, id_clientes, id_produtos FROM avaliacoes";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Avaliacoes avaliacao = new Avaliacoes();
                avaliacao.setId(resultSet.getInt("id_avaliacoes"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getDate("dt_avaliacao")); // Usa java.sql.Date
                avaliacao.setIdClientes(resultSet.getInt("id_clientes")); // Chave estrangeira
                avaliacao.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
                avaliacoesList.add(avaliacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacoesList;
    }

    public Avaliacoes findById(int id_avaliacoes) {
        Avaliacoes avaliacao = null;
        String sql = "SELECT id_avaliacoes, nota, comentario, dt_avaliacao, id_clientes, id_produtos FROM avaliacoes WHERE id_avaliacoes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_avaliacoes);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avaliacao = new Avaliacoes();
                avaliacao.setId(resultSet.getInt("id_avaliacoes"));
                avaliacao.setNota(resultSet.getInt("nota"));
                avaliacao.setComentario(resultSet.getString("comentario"));
                avaliacao.setDtAvaliacao(resultSet.getDate("dt_avaliacao")); // Usa java.sql.Date
                avaliacao.setIdClientes(resultSet.getInt("id_clientes")); // Chave estrangeira
                avaliacao.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avaliacao;
    }

    public boolean save(Avaliacoes avaliacao) {
        String sql = "INSERT INTO avaliacoes (nota, comentario, dt_avaliacao) VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, new Date(avaliacao.getDtAvaliacao().getTime())); // Converte java.util.Date para java.sql.Date

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_avaliacoes, Avaliacoes avaliacao) {
        String sql = "UPDATE avaliacoes SET nota = ?, comentario = ?, dt_avaliacao = ? WHERE id_avaliacoes = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, avaliacao.getNota());
            preparedStatement.setString(2, avaliacao.getComentario());
            preparedStatement.setDate(3, new Date(avaliacao.getDtAvaliacao().getTime())); // Converte java.util.Date para java.sql.Date
            preparedStatement.setInt(4, id_avaliacoes); // ID da avaliação a ser atualizada

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