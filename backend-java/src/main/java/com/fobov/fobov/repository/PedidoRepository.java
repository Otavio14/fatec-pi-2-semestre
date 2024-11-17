package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Pedido;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoRepository implements Crud<Pedido, Integer> {
    private final DataSource DATA_SOURCE;

    public PedidoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidoList = new ArrayList<>();
        String sql = "SELECT id_pedidos, dt_pedido, endereco, status, total, id_clientes FROM pedidos";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id_pedidos"));
                pedido.setDtPedido(resultSet.getTimestamp("dt_pedido").toLocalDateTime());
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdCliente(resultSet.getInt("id_clientes"));
                pedidoList.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidoList;
    }

    public Pedido findById(Integer id) {
        Pedido pedido = null;
        String sql = "SELECT id_pedidos, dt_pedido, endereco, status, total, id_clientes FROM pedidos WHERE id_pedidos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pedido = new Pedido();
                pedido.setId(resultSet.getInt("id_pedidos"));
                pedido.setDtPedido(resultSet.getTimestamp("dt_pedido").toLocalDateTime());
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdCliente(resultSet.getInt("id_clientes"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }

    public boolean save(Pedido pedido) {
        String sql = "INSERT INTO pedidos (dt_pedido, endereco, status, total, id_clientes) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(pedido.getDtPedido().toLocalDate()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setString(3, pedido.getStatus());
            preparedStatement.setDouble(4, pedido.getTotal());
            preparedStatement.setInt(5, pedido.getIdCliente());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, Pedido pedido) {
        String sql = "UPDATE pedidos SET dt_pedido = ?, endereco = ?, status = ?, total = ?, id_clientes = ? WHERE id_pedidos = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(pedido.getDtPedido().toLocalDate()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setDouble(3, pedido.getTotal());
            preparedStatement.setString(4, pedido.getStatus());
            preparedStatement.setInt(5, pedido.getIdCliente());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM pedidos WHERE id_pedidos = ?";

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