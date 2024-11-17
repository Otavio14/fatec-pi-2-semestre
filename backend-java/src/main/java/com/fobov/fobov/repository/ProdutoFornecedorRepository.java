package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoFornecedor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoFornecedorRepository implements Crud<ProdutoFornecedor, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoFornecedorRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutoFornecedor> findAll() {
        List<ProdutoFornecedor> produtoFornecedorList = new ArrayList<>();
        String sql = "SELECT id, preco, quantidade, data, id_produtos, id_fornecedores FROM produtos_fornecedores";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor();
                produtoFornecedor.setId(resultSet.getInt("id"));
                produtoFornecedor.setPreco(resultSet.getDouble("preco"));
                produtoFornecedor.setQuantidade(resultSet.getInt("quantidade"));
                produtoFornecedor.setData(resultSet.getTimestamp("data").toLocalDateTime());
                produtoFornecedor.setIdProduto(resultSet.getInt("id_produtos"));
                produtoFornecedor.setIdFornecedor(resultSet.getInt("id_fornecedores"));
                produtoFornecedorList.add(produtoFornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedorList;
    }

    public ProdutoFornecedor findById(Integer id) {
        ProdutoFornecedor produtoFornecedor = null;
        String sql = "SELECT id, preco, quantidade, data, id_produtos, id_fornecedores FROM produtos_fornecedores WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtoFornecedor = new ProdutoFornecedor();
                produtoFornecedor.setId(resultSet.getInt("id"));
                produtoFornecedor.setPreco(resultSet.getDouble("preco"));
                produtoFornecedor.setQuantidade(resultSet.getInt("quantidade"));
                produtoFornecedor.setData(resultSet.getTimestamp("data").toLocalDateTime());
                produtoFornecedor.setIdProduto(resultSet.getInt("id_produtos"));
                produtoFornecedor.setIdFornecedor(resultSet.getInt("id_fornecedores"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedor;
    }

    public boolean save(ProdutoFornecedor produtoFornecedor) {
        String sql = "INSERT INTO produtos_fornecedores (preco, quantidade, data) VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(produtoFornecedor.getData().toLocalDate()));

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, ProdutoFornecedor produtoFornecedor) {
        String sql = "UPDATE produtos_fornecedores SET preco = ?, quantidade = ?, data = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(produtoFornecedor.getData().toLocalDate()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
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