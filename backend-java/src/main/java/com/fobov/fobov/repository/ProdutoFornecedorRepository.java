package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoFornecedor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoFornecedorRepository
        implements Crud<ProdutoFornecedor, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoFornecedorRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutoFornecedor> findAll() {
        List<ProdutoFornecedor> produtoFornecedorList = new ArrayList<>();
        String sql = "SELECT produtos_fornecedores.id, produtos_fornecedores" +
                ".preco, produtos_fornecedores.quantidade, " +
                "produtos_fornecedores.data, produtos_fornecedores" +
                ".id_produto, produtos_fornecedores.id_fornecedor, " +
                "produtos.nome AS produto, fornecedores.nome AS " +
                "fornecedor FROM produtos_fornecedores LEFT JOIN " +
                "produtos ON produtos_fornecedores.id_produto = " +
                "produtos.id LEFT JOIN fornecedores ON " +
                "produtos_fornecedores.id_fornecedor = fornecedores" + ".id;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor();
                produtoFornecedor.setId(resultSet.getInt("id"));
                produtoFornecedor.setPreco(resultSet.getDouble("preco"));
                produtoFornecedor.setQuantidade(resultSet.getInt("quantidade"));
                produtoFornecedor.setData(
                        resultSet.getTimestamp("data").toLocalDateTime());
                produtoFornecedor.setIdProduto(resultSet.getInt("id_produto"));
                produtoFornecedor.setIdFornecedor(
                        resultSet.getInt("id_fornecedor"));
                produtoFornecedor.setProduto(resultSet.getString("produto"));
                produtoFornecedor.setFornecedor(
                        resultSet.getString("fornecedor"));
                produtoFornecedorList.add(produtoFornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedorList;
    }

    public ProdutoFornecedor findById(Integer id) {
        ProdutoFornecedor produtoFornecedor = null;
        String sql = "SELECT id, preco, quantidade, data, id_produto, " +
                "id_fornecedor FROM produtos_fornecedores WHERE id " + "= ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtoFornecedor = new ProdutoFornecedor();
                produtoFornecedor.setId(resultSet.getInt("id"));
                produtoFornecedor.setPreco(resultSet.getDouble("preco"));
                produtoFornecedor.setQuantidade(resultSet.getInt("quantidade"));
                produtoFornecedor.setData(
                        resultSet.getTimestamp("data").toLocalDateTime());
                produtoFornecedor.setIdProduto(resultSet.getInt("id_produto"));
                produtoFornecedor.setIdFornecedor(
                        resultSet.getInt("id_fornecedor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedor;
    }

    public ResponseEntity<String> save(ProdutoFornecedor produtoFornecedor) {
        String sql =
                "INSERT INTO produtos_fornecedores (preco, quantidade, data) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(
                    produtoFornecedor.getData().toLocalDate()));

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto fornecedor cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id,
                                         ProdutoFornecedor produtoFornecedor) {
        String sql =
                "UPDATE produtos_fornecedores SET preco = ?, quantidade = ?, " +
                        "data = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(
                    produtoFornecedor.getData().toLocalDate()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto fornecedor alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM produtos_fornecedores WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto fornecedor excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}