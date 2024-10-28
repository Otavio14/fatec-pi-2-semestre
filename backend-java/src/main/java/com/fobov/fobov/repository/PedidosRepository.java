package com.fobov.fobov.repository;

import com.fobov.fobov.model.Pedidos;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Repository
public class PedidosRepository {
    private final DataSource DATA_SOURCE;

    public PedidosRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Pedidos> findAll() {
        List<Pedidos> pedidosList = new ArrayList<>();
        String sql = "SELECT id_pedidos, dt_pedido, endereco, status, total, id_clientes FROM pedidos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setId(resultSet.getInt("id_pedidos"));
                pedido.setDtPedido(resultSet.getDate("dt_pedido")); // Usa java.sql.Date
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdClientes(resultSet.getInt("id_clientes"));
                pedidosList.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidosList;
    }

    public Pedidos findById(int id_pedidos) {
        Pedidos pedido = null;
        String sql = "SELECT id_pedidos, dt_pedido, endereco, status, total, id_clientes FROM pedidos WHERE id_pedidos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_pedidos);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pedido = new Pedidos();
                pedido.setId(resultSet.getInt("id_pedidos"));
                pedido.setDtPedido(resultSet.getDate("dt_pedido")); // Usa java.sql.Date
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdClientes(resultSet.getInt("id_clientes"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }

    public boolean save(Pedidos pedido) {
        String sql = "INSERT INTO pedidos (dt_pedido, endereco, status, total, id_clientes) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(pedido.getDtPedido().getTime())); // Converte java.util.Date para java.sql.Date
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setString(3, pedido.getStatus());
            preparedStatement.setDouble(4, pedido.getTotal());
            preparedStatement.setInt(5, pedido.getIdClientes()); // Chave estrangeira

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_pedidos, Pedidos pedido) {
        String sql = "UPDATE pedidos SET dt_pedido = ?, endereco = ?, status = ?, total = ?, id_clientes = ? WHERE id_pedidos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(pedido.getDtPedido().getTime())); // Converte java.util.Date para java.sql.Date
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.set Double(3, pedido.getTotal());
            preparedStatement.setString(4, pedido.getStatus());
            preparedStatement.setInt(5, pedido.getIdClientes()); // Chave estrangeira
            preparedStatement.setInt(6, id_pedidos);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_pedidos) {
        String sql = "DELETE FROM pedidos WHERE id_pedidos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_pedidos);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}