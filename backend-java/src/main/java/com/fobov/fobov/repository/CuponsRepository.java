package com.fobov.fobov.repository;

import com.fobov.fobov.model.Cupons;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class CuponsRepository {
    private final DataSource DATA_SOURCE;

    public CuponsRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Cupons> findAll() {
        List<Cupons> cuponsList = new ArrayList<>();
        String sql = "SELECT id_cupons, nome, porcentagem FROM cupons";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cupons cupom = new Cupons();
                cupom.setId(resultSet.getInt("id_cupons"));
                cupom.setNome(resultSet.getString("nome"));
                cupom.setPorcentagem(resultSet.getDouble("porcentagem"));
                cuponsList.add(cupom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cuponsList;
    }

    public Cupons findById(int id_cupons) {
        Cupons cupom = null;
        String sql = "SELECT id_cupons, nome, porcentagem FROM cupons WHERE id_cupons = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_cupons);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cupom = new Cupons();
                cupom.setId(resultSet.getInt("id_cupons"));
                cupom.setNome(resultSet.getString("nome"));
                cupom.setPorcentagem(resultSet.getDouble("porcentagem"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cupom;
    }

    public boolean save(Cupons cupom) {
        String sql = "INSERT INTO cupons (nome, porcentagem) VALUES (?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cupom.getNome());
            preparedStatement.setDouble(2, cupom.getPorcentagem());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_cupons, Cupons cupom) {
        String sql = "UPDATE cupons SET nome = ?, porcentagem = ? WHERE id_cupons = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cupom.getNome());
            preparedStatement.setDouble(2, cupom.getPorcentagem());
            preparedStatement.setInt(3, id_cupons); // ID do cupom a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_cupons) {
        String sql = "DELETE FROM cupons WHERE id_cupons = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_cupons);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}