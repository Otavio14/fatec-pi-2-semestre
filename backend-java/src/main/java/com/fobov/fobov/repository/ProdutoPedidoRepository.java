package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoPedido;
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
        String sql = "SELECT id, preco, quantidade, id_produtos, id_pedidos FROM produtos_pedidos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutoPedido produtoPedido = new ProdutoPedido();
                produtoPedido.setId(resultSet.getInt("id"));
                produtoPedido.setPreco(resultSet.getDouble("preco"));
                produtoPedido.setQuantidade(resultSet.getInt("quantidade"));
                produtoPedido.setIdProduto(resultSet.getInt("id_produtos"));
                produtoPedido.setIdPedido(resultSet.getInt("id_pedidos"));
                produtoPedidoList.add(produtoPedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedidoList;
    }

    public ProdutoPedido findById(Integer id) {
        ProdutoPedido produtoPedido = null;
        String sql = "SELECT id, preco, quantidade, id_produtos, id_pedidos FROM produtos_pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtoPedido = new ProdutoPedido();
                produtoPedido.setId(resultSet.getInt("id"));
                produtoPedido.setPreco(resultSet.getDouble("preco"));
                produtoPedido.setQuantidade(resultSet.getInt("quantidade"));
                produtoPedido.setIdProduto(resultSet.getInt("id_produtos"));
                produtoPedido.setIdPedido(resultSet.getInt("id_pedidos"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedido;
    }

    public boolean save(ProdutoPedido produtoPedido) {
        String sql = "INSERT INTO produtos_pedidos (preco, quantidade) VALUES (?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtoPedido.getPreco());
            preparedStatement.setInt(2, produtoPedido.getQuantidade());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, ProdutoPedido produtoPedido) {
        String sql = "UPDATE produtos_pedidos SET preco = ?, quantidade = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtoPedido.getPreco());
            preparedStatement.setInt(2, produtoPedido.getQuantidade());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM produtos_pedidos WHERE id = ?";

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