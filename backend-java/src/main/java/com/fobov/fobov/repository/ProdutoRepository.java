package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String sql = "SELECT id, nome, dt_validade, preco, estoque, " +
                "descricao, imagem, ativo FROM produtos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(
                        LocalDate.parse(resultSet.getString("dt_validade"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setImagem(resultSet.getString("imagem"));
                produto.setAtivo(resultSet.getInt("ativo"));
                produtoList.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoList;
    }

    public Produto findById(Integer id) {
        Produto produto = null;
        String sql = "SELECT id, nome, dt_validade, preco, estoque, " +
                "descricao, imagem, ativo FROM produtos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setDtValidade(
                        LocalDate.parse(resultSet.getString("dt_validade"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                produto.setPreco(resultSet.getDouble("preco"));
                produto.setEstoque(resultSet.getInt("estoque"));
                produto.setDescricao(resultSet.getString("descricao"));
                produto.setImagem(resultSet.getString("imagem"));
                produto.setAtivo(resultSet.getInt("ativo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    public ResponseEntity<String> save(Produto produto) {
        String sql =
                "INSERT INTO produtos (nome, dt_validade, preco, estoque, " +
                        "descricao, imagem, ativo) VALUES (?, ?, ?, ?, ?, ?, " +
                        "?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDtValidade()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setString(6, produto.getImagem());
            preparedStatement.setInt(7, produto.getAtivo());


            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Produto produto) {
        String sql =
                "UPDATE produtos SET nome = ?, dt_validade = ?, preco = ?, " +
                        "estoque = ?, descricao = ?, imagem = ?, ativo = ? " +
                        "WHERE " + "id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setString(2, produto.getDtValidade()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.setInt(4, produto.getEstoque());
            preparedStatement.setString(5, produto.getDescricao());
            preparedStatement.setString(6, produto.getImagem());
            preparedStatement.setInt(7, produto.getAtivo());
            preparedStatement.setInt(8, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}