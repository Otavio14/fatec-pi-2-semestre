package com.fobov.fobov.repository;

import com.fobov.fobov.model.Categoria;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepository {
    private final DataSource DATA_SOURCE;

    public CategoriaRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nome FROM categoria";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                categorias.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public Categoria findById(int id) {
        Categoria categoria = new Categoria();
        String sql = "SELECT id, nome FROM categoria WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Check if there is a result
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoria;
    }

    public boolean save(Categoria categoria) {
        String sql = "INSERT INTO categoria (nome) VALUES (?)";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoria.getNome());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id, Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ? WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
