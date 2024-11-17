package com.fobov.fobov.repository;

import com.fobov.fobov.interfaces.Crud;
import com.fobov.fobov.model.Estado;
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
public class EstadoRepository implements Crud<Estado, Integer> {
    private final DataSource DATA_SOURCE;

    public EstadoRepository(DataSource dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public List<Estado> findAll() {
        List<Estado> estados = new ArrayList<>();
        String sql = "SELECT id_estado, nome, sigla FROM estados";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Estado estado = new Estado();
                estado.setId(resultSet.getInt("id_estado"));
                estado.setNome(resultSet.getString("nome"));
                estado.setSigla(resultSet.getString("sigla"));
                estados.add(estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estados;
    }

    public Estado findById(Integer id) {
        Estado estado = new Estado();
        String sql =
                "SELECT id_estado, nome, sigla FROM estados WHERE id_estado =" +
                        " ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                estado.setId(resultSet.getInt("id_estado"));
                estado.setNome(resultSet.getString("nome"));
                estado.setSigla(resultSet.getString("sigla"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }

    public ResponseEntity<String> save(Estado estado) {
        String sql = "INSERT INTO estados (nome, sigla) VALUES (?, ?)";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, estado.getNome());
            preparedStatement.setString(2, estado.getSigla());

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Estado cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar o cadastro!");
    }

    public ResponseEntity<String> update(Integer id, Estado estado) {
        String sql =
                "UPDATE estados SET nome = ?, sigla = ? WHERE id_estado = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setString(1, estado.getNome());
            preparedStatement.setString(2, estado.getSigla());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Estado alterado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a alteração!");
    }

    public ResponseEntity<String> delete(Integer id) {
        String sql = "DELETE FROM estados WHERE id_estado = ?";

        try {
            Connection connection = DATA_SOURCE.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Estado excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível realizar a exclusão!");
    }
}
