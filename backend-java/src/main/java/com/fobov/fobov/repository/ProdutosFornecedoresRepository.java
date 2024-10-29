package com.fobov.fobov.repository;

import com.fobov.fobov.model.ProdutosFornecedores;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class ProdutosFornecedoresRepository {
    private final DataSource DATA_SOURCE;

    public ProdutosFornecedoresRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutosFornecedores> findAll() {
        List<ProdutosFornecedores> produtosFornecedoresList = new ArrayList<>();
        String sql = "SELECT id, preco, quantidade, data, id_produtos, id_fornecedores FROM produtos_fornecedores";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutosFornecedores produtosFornecedores = new ProdutosFornecedores();
                produtosFornecedores.setId(resultSet.getInt("id"));
                produtosFornecedores.setPreco(resultSet.getDouble("preco"));
                produtosFornecedores.setQuantidade(resultSet.getInt("quantidade"));
                produtosFornecedores.setData(resultSet.getDate("data")); // Usa java.sql.Date
                produtosFornecedores.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
                produtosFornecedores.setIdCidades(resultSet.getInt("id_fornecedores")); // Chave estrangeira
                produtosFornecedoresList.add(produtosFornecedores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosFornecedoresList;
    }

    public ProdutosFornecedores findById(int id) {
        ProdutosFornecedores produtosFornecedores = null;
        String sql = "SELECT id, preco, quantidade, data, id_produtos, id_fornecedores FROM produtos_fornecedores WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtosFornecedores = new ProdutosFornecedores();
                produtosFornecedores.setId(resultSet.getInt("id"));
                produtosFornecedores.setPreco(resultSet.getDouble("preco"));
                produtosFornecedores.setQuantidade(resultSet.getInt("quantidade"));
                produtosFornecedores.setData(resultSet.getDate("data")); // Usa java.sql.Date
                produtosFornecedores.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
                produtosFornecedores.setIdCidades(resultSet.getInt("id_fornecedores")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosFornecedores;
    }

    public boolean save(ProdutosFornecedores produtosFornecedores) {
        String sql = "INSERT INTO produtos_fornecedores (preco, quantidade, data) VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtosFornecedores.getPreco());
            preparedStatement.setInt(2, produtosFornecedores.getQuantidade());
            preparedStatement.setDate(3, new Date(produtosFornecedores.getData().getTime())); // Converte java.util.Date para java.sql.Date

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id, ProdutosFornecedores produtosFornecedores) {
        String sql = "UPDATE produtos_fornecedores SET preco = ?, quantidade = ?, data = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtosFornecedores.getPreco());
            preparedStatement.setInt(2, produtosFornecedores.getQuantidade());
            preparedStatement.setDate(3, new Date(produtosFornecedores.getData().getTime())); // Converte java.util.Date para java.sql.Date
            preparedStatement.setInt(4, id); // ID do registro a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM produtos_fornecedores WHERE id = ?";

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