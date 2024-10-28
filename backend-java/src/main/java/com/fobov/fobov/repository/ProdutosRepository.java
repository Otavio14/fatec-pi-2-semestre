package com.fobov.fobov.repository;

import com.fobov.fobov.model.Produtos;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class ProdutosRepository {
    private final DataSource DATA_SOURCE;

    public ProdutosRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Produtos> findAll() {
        List<Produtos> produtosList = new ArrayList<>();
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, id_categoria FROM produtos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Produtos produto = new Produtos();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(resultSet.getDate("dt_validade").toLocalDate()); // Converte para LocalDate
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setIdCategoria(resultSet.getInt("id_categoria"));
                produtosList.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosList;
    }

    public Produtos findById(int id_produtos) {
        Produtos produto = null;
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, id_categoria FROM produtos WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_produtos);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produto = new Produtos();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(resultSet.getDate("dt_validade").toLocalDate()); // Converte para LocalDate
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setIdCategoria(resultSet.getInt("id_categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean save(Produtos produto) {
        String sql = "INSERT INTO produtos (nome, dt_validade, preco, estoque, descricao) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, Date.valueOf(produto.getDtValidade())); // Converte LocalDate para Date
            preparedStatement.setDouble(3, produto.getPreco()); // Usa setDouble
            preparedStatement.setInt(4, produto.getEstoque()); // Usa setInt
            preparedStatement.setString(5, produto.getDescricao());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_produtos, Produtos produto) {
        String sql = "UPDATE produtos SET nome = ?, dt_validade = ?, preco = ?, estoque = ?, descricao = ? WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, Date.valueOf(produto.getDtValidade())); // Converte LocalDate para Date
            preparedStatement.setDouble(3, produto.getPreco()); // Usa setDouble
            preparedStatement.setInt(4, produto.getEstoque()); // Usa setInt
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setInt(6, id_produtos);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_produtos) {
        String sql = "DELETE FROM produtos WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_produtos);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}