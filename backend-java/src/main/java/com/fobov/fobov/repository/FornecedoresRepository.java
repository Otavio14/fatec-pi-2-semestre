package com.fobov.fobov.repository;

import com.fobov.fobov.model.Fornecedores;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FornecedoresRepository {
    private final DataSource DATA_SOURCE;

    public FornecedoresRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Fornecedores> findAll() {
        List<Fornecedores> fornecedoresList = new ArrayList<>();
        String sql = "SELECT id_fornecedores, nome, cep, endereco, complemento, telefone, status, id_cidades FROM fornecedores";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Fornecedores fornecedor = new Fornecedores();
                fornecedor.setId(resultSet.getInt("id_fornecedores"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidades(resultSet.getInt("id_cidades"));
                fornecedoresList.add(fornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedoresList;
    }

    public Fornecedores findById(int id_fornecedores) {
        Fornecedores fornecedor = null;
        String sql = "SELECT id_fornecedores, nome, cep, endereco, complemento, telefone, status, id_cidades FROM fornecedores WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_fornecedores);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fornecedor = new Fornecedores();
                fornecedor.setId(resultSet.getInt("id_fornecedores"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidades(resultSet.getInt("id_cidades"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedor;
    }

    public boolean save(Fornecedores fornecedor) {
        String sql = "INSERT INTO fornecedores (nome, cep, endereco, complemento, telefone, status, id_cidades) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidades());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(int id_fornecedores, Fornecedores fornecedor) {
        String sql = "UPDATE fornecedores SET nome = ?, cep = ?, endereco = ?, complemento = ?, telefone = ?, status = ?, id_cidades = ? WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidades());
            preparedStatement.setInt(8, id_fornecedores);

            preparedStatement.executeUpdate();
 return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id_fornecedores) {
        String sql = "DELETE FROM fornecedores WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_fornecedores);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}