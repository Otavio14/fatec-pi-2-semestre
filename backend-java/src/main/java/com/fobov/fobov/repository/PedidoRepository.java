package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Pedido;
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
public class PedidoRepository implements Crud<Pedido, Integer> {
    private final DataSource DATA_SOURCE;

    public PedidoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidoList = new ArrayList<>();
        String sql =
                "SELECT pedidos.id, pedidos.dt_pedido, pedidos.endereco, " +
                        "pedidos.status, pedidos.total, pedidos.id_cliente, " +
                        "clientes.nome AS cliente FROM pedidos LEFT JOIN " +
                        "clientes ON pedidos.id_cliente = clientes.id;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setDtPedido(
                        resultSet.getTimestamp("dt_pedido").toLocalDateTime());
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdCliente(resultSet.getInt("id_cliente"));
                pedido.setCliente(resultSet.getString("cliente"));
                pedidoList.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidoList;
    }

    public Pedido findById(Integer id) {
        Pedido pedido = null;
        String sql = "SELECT id, dt_pedido, endereco, status, total, " +
                "id_cliente FROM pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setDtPedido(
                        resultSet.getTimestamp("dt_pedido").toLocalDateTime());
                pedido.setEndereco(resultSet.getString("endereco"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setIdCliente(resultSet.getInt("id_cliente"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }

    public ResponseEntity<String> save(Pedido pedido) {
        String sql =
                "INSERT INTO pedidos (dt_pedido, endereco, status, total, " +
                        "id_cliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDate(1,
                    java.sql.Date.valueOf(pedido.getDtPedido().toLocalDate()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setString(3, pedido.getStatus());
            preparedStatement.setDouble(4, pedido.getTotal());
            preparedStatement.setInt(5, pedido.getIdCliente());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Pedido cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Pedido pedido) {
        String sql =
                "UPDATE pedidos SET dt_pedido = ?, endereco = ?, status = ?, " +
                        "total = ?, id_cliente = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDate(1,
                    java.sql.Date.valueOf(pedido.getDtPedido().toLocalDate()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setDouble(3, pedido.getTotal());
            preparedStatement.setString(4, pedido.getStatus());
            preparedStatement.setInt(5, pedido.getIdCliente());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Pedido alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Pedido excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }

    public List<Pedido> findAllByClienteId(Integer id) {
        List<Pedido> pedidoList = new ArrayList<>();
        String sql = "SELECT id, dt_pedido, endereco, status, total, " +
                "id_cliente FROM pedidos WHERE id_cliente = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(resultSet.getInt("id"));
                    pedido.setDtPedido(resultSet.getTimestamp("dt_pedido")
                            .toLocalDateTime());
                    pedido.setEndereco(resultSet.getString("endereco"));
                    pedido.setStatus(resultSet.getString("status"));
                    pedido.setTotal(resultSet.getDouble("total"));
                    pedido.setIdCliente(resultSet.getInt("id_cliente"));
                    pedidoList.add(pedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidoList;
    }
}