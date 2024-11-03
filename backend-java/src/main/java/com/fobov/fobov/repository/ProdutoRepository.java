package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepository implements Crud<Produto, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Produto> findAll() {
        List<Produto> produtoList = new ArrayList<>();
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, id_categoria FROM produtos";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(resultSet.getTimestamp("dt_validade").toLocalDateTime());
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produtoList.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoList;
    }

    public Produto findById(Integer id) {
        Produto produto = null;
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, id_categoria FROM produtos WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produto = new Produto();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(resultSet.getTimestamp("dt_validade").toLocalDateTime());
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, dt_validade, preco, estoque, descricao) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, java.sql.Date.valueOf(produto.getDtValidade().toLocalDate()));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, dt_validade = ?, preco = ?, estoque = ?, descricao = ? WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDate(2, java.sql.Date.valueOf(produto.getDtValidade().toLocalDate()));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM produtos WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}