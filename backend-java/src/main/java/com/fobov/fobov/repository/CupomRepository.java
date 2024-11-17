package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cupom;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CupomRepository implements Crud<Cupom, Integer> {
    private final DataSource DATA_SOURCE;

    public CupomRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Cupom> findAll() {
        List<Cupom> cupomList = new ArrayList<>();
        String sql = "SELECT id_cupons, nome, porcentagem FROM cupons";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cupom cupom = new Cupom();
                cupom.setId(resultSet.getInt("id_cupons"));
                cupom.setNome(resultSet.getString("nome"));
                cupom.setPorcentagem(resultSet.getDouble("porcentagem"));
                cupomList.add(cupom);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cupomList;
    }

    public Cupom findById(Integer id) {
        Cupom cupom = null;
        String sql = "SELECT id_cupons, nome, porcentagem FROM cupons WHERE id_cupons = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cupom = new Cupom();
                cupom.setId(resultSet.getInt("id_cupons"));
                cupom.setNome(resultSet.getString("nome"));
                cupom.setPorcentagem(resultSet.getDouble("porcentagem"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cupom;
    }

    public boolean save(Cupom cupom) {
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

    public boolean update(Integer id, Cupom cupom) {
        String sql = "UPDATE cupons SET nome = ?, porcentagem = ? WHERE id_cupons = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cupom.getNome());
            preparedStatement.setDouble(2, cupom.getPorcentagem());
            preparedStatement.setInt(3, id); // ID do cupom a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM cupons WHERE id_cupons = ?";

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