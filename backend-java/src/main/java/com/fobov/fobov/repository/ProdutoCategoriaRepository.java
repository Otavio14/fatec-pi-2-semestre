package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.ProdutoCategoria;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoCategoriaRepository implements Crud<ProdutoCategoria, Integer> {
    private final DataSource DATA_SOURCE;

    public ProdutoCategoriaRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<ProdutoCategoria> findAll() {
        List<ProdutoCategoria> produtoCategorias = new ArrayList<>();
        String sql = "SELECT id, id_produto, id_categoria FROM produtos_categorias";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProdutoCategoria produtoCategoria = new ProdutoCategoria();
                produtoCategoria.setId(resultSet.getInt("id"));
                produtoCategoria.setIdProduto(resultSet.getInt("id_produto"));
                produtoCategoria.setIdCategoria(resultSet.getInt("id_categoria"));
                produtoCategorias.add(produtoCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoCategorias;
    }

    public ProdutoCategoria findById(Integer id) {
        ProdutoCategoria produtoCategoria = new ProdutoCategoria();
        String sql = "SELECT id, id_produto, id_categoria FROM produtos_categorias WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produtoCategoria.setId(resultSet.getInt("id"));
                produtoCategoria.setIdProduto(resultSet.getInt("id_produto"));
                produtoCategoria.setIdCategoria(resultSet.getInt("id_categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtoCategoria;
    }

    public boolean save(ProdutoCategoria produtoCategoria) {
        String sql = "INSERT INTO produtos_categorias (id_produto, id_categoria) VALUES (?, ?)";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, produtoCategoria.getIdProduto());
            preparedStatement.setInt(2, produtoCategoria.getIdCategoria());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, ProdutoCategoria produtoCategoria) {
        String sql = "UPDATE produtos_categorias SET id_produto = ?, id_categoria = ? WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, produtoCategoria.getIdProduto());
            preparedStatement.setInt(2, produtoCategoria.getIdCategoria());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM produtos_categorias WHERE id = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
