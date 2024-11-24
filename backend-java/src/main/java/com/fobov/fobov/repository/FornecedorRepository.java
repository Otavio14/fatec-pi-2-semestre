package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Fornecedor;
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
public class FornecedorRepository implements Crud<Fornecedor, Integer> {
    private final DataSource DATA_SOURCE;

    public FornecedorRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Fornecedor> findAll() {
        List<Fornecedor> fornecedorList = new ArrayList<>();
        String sql =
                "SELECT fornecedores.id, fornecedores.nome, fornecedores.cep," +
                        " fornecedores.endereco, fornecedores.complemento, " +
                        "fornecedores.telefone, fornecedores.status, " +
                        "fornecedores.id_cidade, cidades.nome AS cidade, " +
                        "estados.nome AS estado FROM fornecedores LEFT JOIN " +
                        "cidades ON cidades.id = fornecedores.id_cidade LEFT " +
                        "JOIN estados ON estados.id = cidades.id_estado;";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidade(resultSet.getInt("id_cidade"));
                fornecedor.setCidade(resultSet.getString("cidade"));
                fornecedor.setEstado(resultSet.getString("estado"));
                fornecedorList.add(fornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedorList;
    }

    public Fornecedor findById(Integer id) {
        Fornecedor fornecedor = null;
        String sql = "SELECT id, nome, cep, endereco, " +
                "complemento, telefone, status, id_cidade FROM fornecedores " +
                "WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidade(resultSet.getInt("id_cidade"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedor;
    }

    public ResponseEntity<String> save(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedores (nome, cep, endereco, " +
                "complemento, telefone, status, id_cidade) VALUES (?, ?, ?, " +
                "?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidade());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Fornecedor cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Fornecedor fornecedor) {
        String sql = "UPDATE fornecedores SET nome = ?, cep = ?, endereco = " +
                "?, complemento = ?, telefone = ?, status = ?, id_cidade = ? " +
                "WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidade());
            preparedStatement.setInt(8, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Fornecedor alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM fornecedores WHERE id = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Fornecedor excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}