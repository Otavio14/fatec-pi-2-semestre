package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, imagem FROM produtos";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(LocalDate.parse(resultSet.getString("dt_validade"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setImagem(resultSet.getString("imagem"));
                produtoList.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoList;
    }

    public Produto findById(Integer id) {
        Produto produto = null;
        String sql = "SELECT id_produtos, nome, dt_validade, preco, estoque, descricao, imagem FROM produtos WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produto = new Produto();
                produto.setId(resultSet.getInt("id_produtos"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(LocalDate.parse(resultSet.getString("dt_validade"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setImagem(resultSet.getString("imagem"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    public boolean save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, dt_validade, preco, estoque, descricao, imagem) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDtValidade().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setString(6, produto.getImagem());


            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, dt_validade = ?, preco = ?, estoque = ?, descricao = ?, imagem = ? WHERE id_produtos = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDtValidade().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setString(6, produto.getImagem());
            preparedStatement.setInt(7, id);

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