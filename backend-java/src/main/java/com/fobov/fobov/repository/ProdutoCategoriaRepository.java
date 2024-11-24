package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Categoria;
import com.fobov.fobov.model.Produto;
import com.fobov.fobov.model.ProdutoCategoria;
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
public class ProdutoCategoriaRepository
        implements Crud<ProdutoCategoria, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoCategoriaRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutoCategoria> findAll() {
        List<ProdutoCategoria> produtoCategorias = new ArrayList<>();
        String sql = "SELECT produtos_categorias.id, produtos_categorias" +
                ".id_produto, produtos_categorias.id_categoria, " +
                "categorias.id AS categoria_id, categorias.nome AS " +
                "categoria_nome, produtos.id AS produto_id, produtos" +
                ".nome AS produto_nome, produtos.dt_validade AS " +
                "produto_dt_validade, produtos.preco AS " +
                "produto_preco, produtos.estoque AS produto_estoque, " +
                "produtos.descricao AS produto_descricao, produtos" +
                ".imagem AS produto_imagem, produtos.ativo AS " +
                "produto_ativo FROM produtos_categorias LEFT JOIN " +
                "produtos ON produtos_categorias.id_produto = " +
                "produtos.id LEFT JOIN categorias ON " +
                "produtos_categorias.id_categoria = categorias.id;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ProdutoCategoria produtoCategoria = new ProdutoCategoria();
                produtoCategoria.setId(resultSet.getInt("id"));
                produtoCategoria.setIdProduto(resultSet.getInt("id_produto"));
                produtoCategoria.setIdCategoria(
                        resultSet.getInt("id_categoria"));

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

                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("categoria_id"));
                categoria.setNome(resultSet.getString("categoria_nome"));

                produtoCategoria.setProduto(produto);
                produtoCategoria.setCategoria(categoria);
                produtoCategorias.add(produtoCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoCategorias;
    }

    public ProdutoCategoria findById(Integer id) {
        ProdutoCategoria produtoCategoria = new ProdutoCategoria();
        String sql =
                "SELECT id, id_produto, id_categoria FROM produtos_categorias" +
                        " WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produtoCategoria.setId(resultSet.getInt("id"));
                    produtoCategoria.setIdProduto(
                            resultSet.getInt("id_produto"));
                    produtoCategoria.setIdCategoria(
                            resultSet.getInt("id_categoria"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoCategoria;
    }

    public ResponseEntity<String> save(ProdutoCategoria produtoCategoria) {
        String sql =
                "INSERT INTO produtos_categorias (id_produto, id_categoria) " +
                        "VALUES (?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, produtoCategoria.getIdProduto());
            preparedStatement.setInt(2, produtoCategoria.getIdCategoria());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto categoria cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id,
                                         ProdutoCategoria produtoCategoria) {
        String sql =
                "UPDATE produtos_categorias SET id_produto = ?, id_categoria " +
                        "= ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, produtoCategoria.getIdProduto());
            preparedStatement.setInt(2, produtoCategoria.getIdCategoria());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto categoria alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM produtos_categorias WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto categoria excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }

    public List<ProdutoCategoria> findAllByProdutoId(Integer id) {
        List<ProdutoCategoria> produtoCategorias = new ArrayList<>();
        String sql = "SELECT produtos_categorias.id, produtos_categorias" +
                ".id_produto, produtos_categorias.id_categoria, " +
                "categorias.id AS categoria_id, categorias.nome AS " +
                "categoria_nome, produtos.id AS produto_id, produtos" +
                ".nome AS produto_nome, produtos.dt_validade AS " +
                "produto_dt_validade, produtos.preco AS " +
                "produto_preco, produtos.estoque AS produto_estoque, " +
                "produtos.descricao AS produto_descricao, produtos" +
                ".imagem AS produto_imagem, produtos.ativo AS " +
                "produto_ativo FROM produtos_categorias LEFT JOIN " +
                "produtos ON produtos_categorias.id_produto = " +
                "produtos.id LEFT JOIN categorias ON " +
                "produtos_categorias.id_categoria = categorias.id " +
                "WHERE produtos_categorias.id_produto = ?;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ProdutoCategoria produtoCategoria = new ProdutoCategoria();
                    produtoCategoria.setId(resultSet.getInt("id"));
                    produtoCategoria.setIdProduto(
                            resultSet.getInt("id_produto"));
                    produtoCategoria.setIdCategoria(
                            resultSet.getInt("id_categoria"));

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

                    Categoria categoria = new Categoria();
                    categoria.setId(resultSet.getInt("categoria_id"));
                    categoria.setNome(resultSet.getString("categoria_nome"));

                    produtoCategoria.setProduto(produto);
                    produtoCategoria.setCategoria(categoria);
                    produtoCategorias.add(produtoCategoria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoCategorias;
    }
}
