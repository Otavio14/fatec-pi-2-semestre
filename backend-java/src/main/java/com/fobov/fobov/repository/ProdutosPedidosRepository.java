package com.fobov.fobov.repository;

import com.fobov.fobov.model.ProdutosPedidos;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class ProdutosPedidosRepository {
    private final DataSource DATA_SOURCE;

    public ProdutosPedidosRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutosPedidos> findAll() {
        List<ProdutosPedidos> produtosPedidosList = new ArrayList<>();
        String sql = "SELECT id, preco, quantidade, id_produtos, id_pedidos FROM produtos_pedidos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutosPedidos produtosPedidos = new ProdutosPedidos();
                produtosPedidos.setId(resultSet.getInt("id"));
                produtosPedidos.setPreco(resultSet.getDouble("preco"));
                produtosPedidos.setQuantidade(resultSet.getInt("quantidade"));
                produtosPedidos.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
                produtosPedidos.setIdCidades(resultSet.getInt("id_pedidos")); // Chave estrangeira
                produtosPedidosList.add(produtosPedidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosPedidosList;
    }

    public ProdutosPedidos findById(int id) {
        ProdutosPedidos produtosPedidos = null;
        String sql = "SELECT id, preco, quantidade, id_produtos, id_pedidos FROM produtos_pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtosPedidos = new ProdutosPedidos();
                produtosPedidos.setId(resultSet.getInt("id"));
                produtosPedidos.setPreco(resultSet.getDouble("preco"));
                produtosPedidos.setQuantidade(resultSet.getInt("quantidade"));
                produtosPedidos.setIdCidades(resultSet.getInt("id_produtos")); // Chave estrangeira
                produtosPedidos.setIdCidades(resultSet.getInt("id_pedidos")); // Chave estrangeira
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtosPedidos;
    }

    public boolean save(ProdutosPedidos produtosPedidos) {
        String sql = "INSERT INTO produtos_pedidos (preco, quantidade) VALUES (?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtosPedidos.getPreco());
            preparedStatement.setInt(2, produtosPedidos.getQuantidade());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id, ProdutosPedidos produtosPedidos) {
        String sql = "UPDATE produtos_pedidos SET preco = ?, quantidade = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, produtosPedidos.getPreco());
            preparedStatement.setInt(2, produtosPedidos.getQuantidade());
            preparedStatement.setInt(3, id); // ID do registro a ser atualizado

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
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