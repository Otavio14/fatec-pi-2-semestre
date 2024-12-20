package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Produto;
import com.fobov.fobov.model.ProdutoPedido;
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
public class ProdutoPedidoRepository implements Crud<ProdutoPedido, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoPedidoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    public List<ProdutoPedido> findAll() {
        List<ProdutoPedido> produtoPedidoList = new ArrayList<>();
        String sql = "SELECT produtos_pedidos.id, produtos_pedidos.preco, " +
                "produtos_pedidos.quantidade, produtos_pedidos" +
                ".id_produto, produtos_pedidos.id_pedido, produtos.id" +
                " AS produto_id, produtos.nome AS produto_nome, " +
                "produtos.dt_validade AS produto_dt_validade, " +
                "produtos.preco AS produto_preco, produtos.estoque AS" +
                " produto_estoque, produtos.descricao AS " +
                "produto_descricao, produtos.imagem AS " +
                "produto_imagem, produtos.ativo AS produto_ativo " +
                "FROM produtos_pedidos LEFT JOIN produtos ON produtos" +
                ".id = produtos_pedidos.id_produto;";

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

                Produto produto = new Produto();
                produto.setId(resultSet.getInt("produto_id"));
                produto.setNome(resultSet.getString("produto_nome"));
                produto.setDtValidade(LocalDate.parse(
                        resultSet.getString("produto_dt_validade"),
                        DateTimeFormatter.ofPattern("yyyy" + "-MM" + "-dd")));
                produto.setPreco(resultSet.getDouble("produto_preco"));
                produto.setEstoque(resultSet.getInt("produto_estoque"));
                produto.setDescricao(resultSet.getString("produto_descricao"));
                produto.setImagem(resultSet.getString("produto_imagem"));
                produto.setAtivo(resultSet.getInt("produto_ativo"));
                produtoPedido.setProduto(produto);
                produtoPedidoList.add(produtoPedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedidoList;
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
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

    /**
     * Cadastrar um novo registro
     *
     * @param produtoPedido - Dados do registro
     * @return resposta da operacao
     */
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

    /**
     * Alterar um registro
     *
     * @param id            - ID do registro
     * @param produtoPedido - Dados do registro
     * @return resposta da operacao
     */
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

    /**
     * Remover um registro
     *
     * @param id - ID do registro
     * @return resposta da operacao
     */
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

    /**
     * Cadastrar uma lista de registro
     *
     * @param produtosPedido - Dados dos registros
     * @return resposta da operacao
     */
    public ResponseEntity<String> saveMany(List<ProdutoPedido> produtosPedido) {
        if (produtosPedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A lista de produtos pedidos está vazia!");
        }

        String deleteSql = "DELETE FROM produtos_pedidos WHERE id_pedido = ?;";
        StringBuilder insertSql = new StringBuilder(
                "INSERT INTO produtos_pedidos (id_produto, id_pedido, preco, " +
                        "quantidade) VALUES ");

        for (int i = 0; i < produtosPedido.size(); i++) {
            insertSql.append("(?, ?, ?, ?)");
            if (i < produtosPedido.size() - 1) {
                insertSql.append(", ");
            }
        }

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     deleteSql);
             PreparedStatement insertStatement = connection.prepareStatement(
                     insertSql.toString())) {

            // Set the id_pedido for the DELETE statement
            deleteStatement.setInt(1, produtosPedido.get(0).getIdPedido());
            deleteStatement.executeUpdate();

            // Set the values for the INSERT statement
            int parameterIndex = 1;
            for (ProdutoPedido produtoPedido : produtosPedido) {
                insertStatement.setInt(parameterIndex++,
                        produtoPedido.getIdProduto());
                insertStatement.setInt(parameterIndex++,
                        produtoPedido.getIdPedido());
                insertStatement.setDouble(parameterIndex++,
                        produtoPedido.getPreco());
                insertStatement.setInt(parameterIndex++,
                        produtoPedido.getQuantidade());
            }

            insertStatement.executeUpdate();

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto pedido cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Não foi possível realizar o cadastro!");
        }
    }

    /**
     * Listar todos os registros com um ID de pedido
     *
     * @param id - ID do pedido
     * @return lista de registros
     */
    public List<ProdutoPedido> findAllByPedidoId(Integer id) {
        List<ProdutoPedido> produtoPedidoList = new ArrayList<>();
        String sql = "SELECT produtos_pedidos.id, produtos_pedidos.preco, " +
                "produtos_pedidos.quantidade, produtos_pedidos" +
                ".id_produto, produtos_pedidos.id_pedido, produtos.id" +
                " AS produto_id, produtos.nome AS produto_nome, " +
                "produtos.dt_validade AS produto_dt_validade, " +
                "produtos.preco AS produto_preco, produtos.estoque AS" +
                " produto_estoque, produtos.descricao AS " +
                "produto_descricao, produtos.imagem AS " +
                "produto_imagem, produtos.ativo AS produto_ativo " +
                "FROM produtos_pedidos LEFT JOIN produtos ON produtos" +
                ".id = produtos_pedidos.id_produto WHERE " +
                "produtos_pedidos.id_pedido = ?;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ProdutoPedido produtoPedido = new ProdutoPedido();
                    produtoPedido.setId(resultSet.getInt("id"));
                    produtoPedido.setPreco(resultSet.getDouble("preco"));
                    produtoPedido.setQuantidade(resultSet.getInt("quantidade"));
                    produtoPedido.setIdProduto(resultSet.getInt("id_produto"));
                    produtoPedido.setIdPedido(resultSet.getInt("id_pedido"));

                    Produto produto = new Produto();
                    produto.setId(resultSet.getInt("produto_id"));
                    produto.setNome(resultSet.getString("produto_nome"));
                    produto.setDtValidade(LocalDate.parse(
                            resultSet.getString("produto_dt_validade"),
                            DateTimeFormatter.ofPattern(
                                    "yyyy" + "-MM" + "-dd")));
                    produto.setPreco(resultSet.getDouble("produto_preco"));
                    produto.setEstoque(resultSet.getInt("produto_estoque"));
                    produto.setDescricao(
                            resultSet.getString("produto_descricao"));
                    produto.setImagem(resultSet.getString("produto_imagem"));
                    produto.setAtivo(resultSet.getInt("produto_ativo"));
                    produtoPedido.setProduto(produto);
                    produtoPedidoList.add(produtoPedido);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoPedidoList;
    }
}