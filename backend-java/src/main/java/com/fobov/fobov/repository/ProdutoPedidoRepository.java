package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoPedido;
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
public class ProdutoPedidoRepository implements Crud<ProdutoPedido, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoPedidoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutoPedido> findAll() {
        List<ProdutoPedido> produtoPedidoList = new ArrayList<>();
        String sql =
                "SELECT id, preco, quantidade, id_produto, id_pedido FROM " +
                        "produtos_pedidos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutoPedido produtoPedido = new ProdutoPedido();
                produtoPedido.setId(resultSet.getInt("id"));
                produtoPedido.setPreco(resultSet.getDouble("preco"));
                produtoPedido.setQuantidade(resultSet.getInt("quantidade"));
                produtoPedido.setIdProduto(resultSet.getInt("id_produto"));
                produtoPedido.setIdPedido(resultSet.getInt("id_pedido"));
                produtoPedidoList.add(produtoPedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedidoList;
    }

    public ProdutoPedido findById(Integer id) {
        ProdutoPedido produtoPedido = null;
        String sql =
                "SELECT id, preco, quantidade, id_produto, id_pedido FROM " +
                        "produtos_pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtoPedido = new ProdutoPedido();
                produtoPedido.setId(resultSet.getInt("id"));
                produtoPedido.setPreco(resultSet.getDouble("preco"));
                produtoPedido.setQuantidade(resultSet.getInt("quantidade"));
                produtoPedido.setIdProduto(resultSet.getInt("id_produto"));
                produtoPedido.setIdPedido(resultSet.getInt("id_pedido"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedido;
    }

    public ResponseEntity<String> save(ProdutoPedido produtoPedido) {
        String sql =
                "INSERT INTO produtos_pedidos (preco, quantidade) VALUES (?, " +
                        "?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoPedido.getPreco());
            preparedStatement.setInt(2, produtoPedido.getQuantidade());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto pedido cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id,
                                         ProdutoPedido produtoPedido) {
        String sql =
                "UPDATE produtos_pedidos SET preco = ?, quantidade = ? WHERE " +
                        "id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoPedido.getPreco());
            preparedStatement.setInt(2, produtoPedido.getQuantidade());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto pedido alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM produtos_pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto pedido excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}