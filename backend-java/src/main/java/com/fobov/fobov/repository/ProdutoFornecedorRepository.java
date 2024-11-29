package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Fornecedor;
import com.fobov.fobov.model.Produto;
import com.fobov.fobov.model.ProdutoFornecedor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoFornecedorRepository
        implements Crud<ProdutoFornecedor, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoFornecedorRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    public List<ProdutoFornecedor> findAll() {
        List<ProdutoFornecedor> produtoFornecedorList = new ArrayList<>();
        String sql = "SELECT produtos_fornecedores.id, produtos_fornecedores" +
                ".preco, produtos_fornecedores.quantidade, " +
                "produtos_fornecedores.data, produtos_fornecedores" +
                ".id_produto, produtos_fornecedores.id_fornecedor, " +
                "produtos.id AS produto_id, produtos.nome AS " +
                "produto_nome, produtos.dt_validade AS " +
                "produto_dt_validade, produtos.preco AS " +
                "produto_preco, produtos.estoque AS produto_estoque, " +
                "produtos.descricao AS produto_descricao, produtos" +
                ".imagem AS produto_imagem, produtos.ativo AS " +
                "produto_ativo, fornecedores.id AS fornecedor_id, " +
                "fornecedores.nome AS fornecedor_nome, fornecedores" +
                ".id_cidade AS fornecedor_id_cidade, fornecedores.cep" +
                " AS fornecedor_cep, fornecedores.endereco AS " +
                "fornecedor_endereco, fornecedores.complemento AS " +
                "fornecedor_complemento, fornecedores.telefone AS " +
                "fornecedor_telefone, fornecedores.status AS " +
                "fornecedor_status FROM produtos_fornecedores LEFT " +
                "JOIN produtos ON produtos_fornecedores.id_produto = " +
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


                Produto produto = new Produto();
                produto.setId(resultSet.getInt("produto_id"));
                produto.setNome(resultSet.getString("produto_nome"));
                produto.setDtValidade(LocalDate.parse(
                        resultSet.getString("produto_dt_validade"),
                        DateTimeFormatter.ofPattern("yyyy-MM" + "-dd")));
                produto.setPreco(resultSet.getDouble("produto_preco"));
                produto.setEstoque(resultSet.getInt("produto_estoque"));
                produto.setDescricao(resultSet.getString("produto_descricao"));
                produto.setImagem(resultSet.getString("produto_imagem"));
                produto.setAtivo(resultSet.getInt("produto_ativo"));


                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("fornecedor_id"));
                fornecedor.setNome(resultSet.getString("fornecedor_nome"));
                fornecedor.setCep(resultSet.getString("fornecedor_cep"));
                fornecedor.setEndereco(
                        resultSet.getString("fornecedor_endereco"));
                fornecedor.setComplemento(
                        resultSet.getString("fornecedor_complemento"));
                fornecedor.setTelefone(
                        resultSet.getString("fornecedor_telefone"));
                fornecedor.setStatus(resultSet.getString("fornecedor_status"));
                fornecedor.setIdCidade(
                        resultSet.getInt("fornecedor_id_cidade"));

                produtoFornecedor.setProduto(produto);
                produtoFornecedor.setFornecedor(fornecedor);
                produtoFornecedorList.add(produtoFornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedorList;
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
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

    /**
     * Cadastrar um novo registro
     *
     * @param produtoFornecedor - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> save(ProdutoFornecedor produtoFornecedor) {
        String sql =
                "INSERT INTO produtos_fornecedores (preco, quantidade, data, " +
                        "id_produto, id_fornecedor) VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(produtoFornecedor.getData()));
            preparedStatement.setInt(4, produtoFornecedor.getIdProduto());
            preparedStatement.setInt(5, produtoFornecedor.getIdFornecedor());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto fornecedor cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    /**
     * Alterar um registro
     *
     * @param id                - ID do registro
     * @param produtoFornecedor - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> update(Integer id,
                                         ProdutoFornecedor produtoFornecedor) {
        String sql =
                "UPDATE produtos_fornecedores SET preco = ?, quantidade = ?, " +
                        "data = ?, id_produto = ?, id_fornecedor = ? WHERE id" +
                        " = ?;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setDouble(1, produtoFornecedor.getPreco());
            preparedStatement.setInt(2, produtoFornecedor.getQuantidade());
            preparedStatement.setTimestamp(3,
                    Timestamp.valueOf(produtoFornecedor.getData()));
            preparedStatement.setInt(4, produtoFornecedor.getIdProduto());
            preparedStatement.setInt(5, produtoFornecedor.getIdFornecedor());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto fornecedor alterado com sucesso!");
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

    /**
     * Listar todos os registros com um ID de fornecedor
     *
     * @param id - ID do fornecedor
     * @return lista de registros
     */
    public List<ProdutoFornecedor> findAllByFornecedorId(Integer id) {
        List<ProdutoFornecedor> produtoFornecedorList = new ArrayList<>();
        String sql = "SELECT produtos_fornecedores.id, produtos_fornecedores" +
                ".preco, produtos_fornecedores.quantidade, " +
                "produtos_fornecedores.data, produtos_fornecedores" +
                ".id_produto, produtos_fornecedores.id_fornecedor, " +
                "produtos.id AS produto_id, produtos.nome AS " +
                "produto_nome, produtos.dt_validade AS " +
                "produto_dt_validade, produtos.preco AS " +
                "produto_preco, produtos.estoque AS produto_estoque, " +
                "produtos.descricao AS produto_descricao, produtos" +
                ".imagem AS produto_imagem, produtos.ativo AS " +
                "produto_ativo, fornecedores.id AS fornecedor_id, " +
                "fornecedores.nome AS fornecedor_nome, fornecedores" +
                ".id_cidade AS fornecedor_id_cidade, fornecedores.cep" +
                " AS fornecedor_cep, fornecedores.endereco AS " +
                "fornecedor_endereco, fornecedores.complemento AS " +
                "fornecedor_complemento, fornecedores.telefone AS " +
                "fornecedor_telefone, fornecedores.status AS " +
                "fornecedor_status FROM produtos_fornecedores LEFT " +
                "JOIN produtos ON produtos_fornecedores.id_produto = " +
                "produtos.id LEFT JOIN fornecedores ON " +
                "produtos_fornecedores.id_fornecedor = fornecedores" +
                ".id WHERE produtos_fornecedores.id_fornecedor = ?;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ProdutoFornecedor produtoFornecedor =
                            new ProdutoFornecedor();
                    produtoFornecedor.setId(resultSet.getInt("id"));
                    produtoFornecedor.setPreco(resultSet.getDouble("preco"));
                    produtoFornecedor.setQuantidade(
                            resultSet.getInt("quantidade"));
                    produtoFornecedor.setData(
                            resultSet.getTimestamp("data").toLocalDateTime());
                    produtoFornecedor.setIdProduto(
                            resultSet.getInt("id_produto"));
                    produtoFornecedor.setIdFornecedor(
                            resultSet.getInt("id_fornecedor"));


                    Produto produto = new Produto();
                    produto.setId(resultSet.getInt("produto_id"));
                    produto.setNome(resultSet.getString("produto_nome"));
                    produto.setDtValidade(LocalDate.parse(
                            resultSet.getString("produto_dt_validade"),
                            DateTimeFormatter.ofPattern("yyyy-MM" + "-dd")));
                    produto.setPreco(resultSet.getDouble("produto_preco"));
                    produto.setEstoque(resultSet.getInt("produto_estoque"));
                    produto.setDescricao(
                            resultSet.getString("produto_descricao"));
                    produto.setImagem(resultSet.getString("produto_imagem"));
                    produto.setAtivo(resultSet.getInt("produto_ativo"));


                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setId(resultSet.getInt("fornecedor_id"));
                    fornecedor.setNome(resultSet.getString("fornecedor_nome"));
                    fornecedor.setCep(resultSet.getString("fornecedor_cep"));
                    fornecedor.setEndereco(
                            resultSet.getString("fornecedor_endereco"));
                    fornecedor.setComplemento(
                            resultSet.getString("fornecedor_complemento"));
                    fornecedor.setTelefone(
                            resultSet.getString("fornecedor_telefone"));
                    fornecedor.setStatus(
                            resultSet.getString("fornecedor_status"));
                    fornecedor.setIdCidade(
                            resultSet.getInt("fornecedor_id_cidade"));

                    produtoFornecedor.setProduto(produto);
                    produtoFornecedor.setFornecedor(fornecedor);
                    produtoFornecedorList.add(produtoFornecedor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoFornecedorList;
    }
}