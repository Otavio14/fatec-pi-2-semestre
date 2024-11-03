package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Fornecedor;
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
        String sql = "SELECT id_fornecedores, nome, cep, endereco, complemento, telefone, status, id_cidades FROM fornecedores";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id_fornecedores"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidade(resultSet.getInt("id_cidades"));
                fornecedorList.add(fornecedor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedorList;
    }

    public Fornecedor findById(Integer id) {
        Fornecedor fornecedor = null;
        String sql = "SELECT id_fornecedores, nome, cep, endereco, complemento, telefone, status, id_cidades FROM fornecedores WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fornecedor = new Fornecedor();
                fornecedor.setId(resultSet.getInt("id_fornecedores"));
                fornecedor.setNome(resultSet.getString("nome"));
                fornecedor.setCep(resultSet.getString("cep"));
                fornecedor.setEndereco(resultSet.getString("endereco"));
                fornecedor.setComplemento(resultSet.getString("complemento"));
                fornecedor.setTelefone(resultSet.getString("telefone"));
                fornecedor.setStatus(resultSet.getString("status"));
                fornecedor.setIdCidade(resultSet.getInt("id_cidades"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fornecedor;
    }

    public boolean save(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedores (nome, cep, endereco, complemento, telefone, status, id_cidades) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidade());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Integer id, Fornecedor fornecedor) {
        String sql = "UPDATE fornecedores SET nome = ?, cep = ?, endereco = ?, complemento = ?, telefone = ?, status = ?, id_cidades = ? WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, fornecedor.getNome());
            preparedStatement.setString(2, fornecedor.getCep());
            preparedStatement.setString(3, fornecedor.getEndereco());
            preparedStatement.setString(4, fornecedor.getComplemento());
            preparedStatement.setString(5, fornecedor.getTelefone());
            preparedStatement.setString(6, fornecedor.getStatus());
            preparedStatement.setInt(7, fornecedor.getIdCidade());
            preparedStatement.setInt(8, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(Integer id) {
        String sql = "DELETE FROM fornecedores WHERE id_fornecedores = ?";

        try (Connection connection = DATA_SOURCE.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}