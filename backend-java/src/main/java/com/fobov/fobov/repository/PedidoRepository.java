package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Cidade;
import com.fobov.fobov.model.Cliente;
import com.fobov.fobov.model.Estado;
import com.fobov.fobov.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
                        "clientes.id AS cliente_id, clientes.nome AS " +
                        "cliente_nome, clientes.id_cidade AS " +
                        "cliente_id_cidade, clientes.cep AS cliente_cep, " +
                        "clientes.endereco AS cliente_endereco, clientes" +
                        ".telefone AS cliente_telefone, clientes.bairro AS " +
                        "cliente_bairro, clientes.numero AS cliente_numero, " +
                        "clientes.email AS cliente_email, cidades.id AS " +
                        "cidade_id, cidades.nome AS cidade_nome, cidades" +
                        ".id_estado AS cidade_id_estado, estados.id AS " +
                        "estado_id, estados.nome AS estado_nome, estados" +
                        ".sigla AS estado_sigla FROM pedidos LEFT JOIN " +
                        "clientes ON clientes.id = pedidos.id_cliente LEFT " +
                        "JOIN cidades ON cidades.id = clientes.id_cidade LEFT" +
                        " JOIN estados ON estados.id = cidades.id_estado;";

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

                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("cliente_id"));
                cliente.setNome(resultSet.getString("cliente_nome"));
                cliente.setCep(resultSet.getString("cliente_cep"));
                cliente.setEndereco(resultSet.getString("cliente_endereco"));
                cliente.setEmail(resultSet.getString("cliente_email"));
                cliente.setTelefone(resultSet.getString("cliente_telefone"));
                cliente.setBairro(resultSet.getString("cliente_bairro"));
                cliente.setNumero(resultSet.getString("cliente_numero"));
                cliente.setIdCidade(resultSet.getInt("cliente_id_cidade"));

                Cidade cidade = new Cidade();
                cidade.setId(resultSet.getInt("cidade_id"));
                cidade.setNome(resultSet.getString("cidade_nome"));
                cidade.setIdEstado(resultSet.getInt("cidade_id_estado"));
                cliente.setCidade(cidade);

                Estado estado = new Estado();
                estado.setId(resultSet.getInt("estado_id"));
                estado.setNome(resultSet.getString("estado_nome"));
                estado.setSigla(resultSet.getString("estado_sigla"));
                cliente.setEstado(estado);

                pedido.setCliente(cliente);
                pedidoList.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidoList;
    }

    public Pedido findById(Integer id) {
        Pedido pedido = new Pedido();
        String sql = "SELECT id, dt_pedido, endereco, status, total, " +
                "id_cliente FROM pedidos WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
                     sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1,
                    Timestamp.valueOf(pedido.getDtPedido()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setString(3, pedido.getStatus());
            preparedStatement.setDouble(4, pedido.getTotal());
            preparedStatement.setInt(5, pedido.getIdCliente());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys =
                         preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(String.valueOf(id));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Não foi possível obter o ID do pedido " +
                                    "cadastrado!");
                }
            }
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
            preparedStatement.setTimestamp(1,
                    Timestamp.valueOf(pedido.getDtPedido()));
            preparedStatement.setString(2, pedido.getEndereco());
            preparedStatement.setString(3, pedido.getStatus());
            preparedStatement.setDouble(4, pedido.getTotal());
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