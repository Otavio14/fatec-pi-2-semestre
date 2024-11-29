package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Categoria;
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
public class CategoriaRepository implements Crud<Categoria, Integer> {
    private final DataSource DATA_SOURCE;

    public CategoriaRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    /**
     * Listar todos os registros
     *
     * @return lista com os registros
     */
    public List<Categoria> findAll() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nome FROM categorias";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                categorias.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    /**
     * Lista os dados de um registro específico
     *
     * @param id - ID do registro
     * @return dados do registro
     */
    public Categoria findById(Integer id) {
        Categoria categoria = new Categoria();
        String sql = "SELECT id, nome FROM categorias WHERE id " + "= ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoria.setId(resultSet.getInt("id"));
                    categoria.setNome(resultSet.getString("nome"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoria;
    }

    /**
     * Cadastrar um novo registro
     *
     * @param categoria - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> save(Categoria categoria) {
        String sql = "INSERT INTO categorias (nome) VALUES (?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {

            preparedStatement.setString(1, categoria.getNome());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Categoria cadastrada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    /**
     * Alterar um registro
     *
     * @param id        - ID do registro
     * @param categoria - Dados do registro
     * @return resposta da operacao
     */
    public ResponseEntity<String> update(Integer id, Categoria categoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {

            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Categoria alterada com sucesso!");
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
        String sql = "DELETE FROM categorias WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Categoria excluída com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}
