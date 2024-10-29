package com.fobov.fobov.repository;

import com.fobov.fobov.model.Cidades;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class CidadesRepository {
    private final DataSource DATA_SOURCE;

    public CidadesRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Cidades> findAll() {
        List<Cidades> cidadesList = new ArrayList<>();
        String sql = "SELECT id_cidades, nome, id_estados FROM cidades";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cidades cidade = new Cidades();
                cidade.setId(resultSet.getInt("id_cidades"));
                cidade.setNome(resultSet.getString("nome"));
                cidade.setIdCidades(resultSet.getInt("id_estados")); // Chave estrangeira
                cidadesList.add(cidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cidadesList;
    }

    public Cidades findById(int id_cidades) {
        Cidades cidade = null;
        String sql = "SELECT id_cidades, nome, id_estados FROM cidades WHERE id_cidades = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_cidades);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cidade = new Cidades();
                cidade.setId(resultSet.getInt("id_cidades"));
                cidade.setNome(resultSet.getString("nome"));
                cidade.setIdCidades(resultSet.getInt("id_estados")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cidade;
    }

    public boolean save(Cidades cidade) {
        String sql = "INSERT INTO cidades (nome) VALUES (?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cidade.getNome());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_cidades, Cidades cidade) {
        String sql = "UPDATE cidades SET nome = ? WHERE id_cidades = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cidade.getNome());
            preparedStatement.setInt(2, id_cidades); // ID da cidade a ser atualizada

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_cidades) {
        String sql = "DELETE FROM cidades WHERE id_cidades = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_cidades);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}