package miau.auau.amigosdequatropatas.dao;
import miau.auau.amigosdequatropatas.entities.AgendarMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgendarMedicamentoDAO implements IDAL<AgendarMedicamento> {

    @Override
    public boolean gravar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = """
                INSERT INTO agendar_medicamento (
                    agemed_medicamento_id, agemed_animal_id, agemed_dataAplicacao, agemed_status
                    )VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1,entidade.getCodMedicamento());
            stmt.setInt(2, entidade.getCodAnimal());

            // Converte a String de data para o formato de Date do SQL
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(entidade.getDataAplicacao());

            stmt.setDate(3, new Date(parsedDate.getTime()));

            stmt.setBoolean(4, entidade.getStatus());

            return stmt.executeUpdate() > 0;
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace(); // ou logue o erro
            return false;
        }

    }

    @Override
    public boolean alterar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = """
            UPDATE agendar_medicamento
            SET agemed_medicamento_id = ?, agemed_animal_id = ?, agemed_dataAplicacao = ?, agemed_status = ?
            WHERE agemed_id = ?
            """;
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1,entidade.getCodMedicamento());
            stmt.setInt(2, entidade.getCodAnimal());

            // Converte a String de data para o formato de Date do SQL
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(entidade.getDataAplicacao());
            stmt.setDate(3, new Date(parsedDate.getTime()));

            stmt.setBoolean(4, entidade.getStatus());

            stmt.setInt(5, entidade.getCodAgendarMedicamento());


            return stmt.executeUpdate() > 0;
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace(); // ou logue o erro
            return false;
        }
    }

    @Override
    public boolean apagar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = "DELETE FROM agendar_medicamento WHERE agemed_id = " + entidade.getCodAgendarMedicamento();

        System.out.println("SQL executado: " + sql);

        return conexao.manipular(sql);
    }

    @Override
    public AgendarMedicamento get(int id, Conexao conexao) {
        AgendarMedicamento agendarMedicamento = null;
        String sql = "SELECT * FROM agendar_medicamento WHERE agemed_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                agendarMedicamento = new AgendarMedicamento(
                        id,
                        resultSet.getInt("agemed_medicamento_id"),
                        resultSet.getInt("agemed_animal_id"),
                        resultSet.getString("agemed_dataAplicacao"),
                        resultSet.getBoolean("agemed_status") // Recupera o status do banco
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agendarMedicamento;
    }

    @Override
    public List<AgendarMedicamento> get(String filtro, Conexao conexao) {
        List<AgendarMedicamento> lista = new ArrayList<>();

        //System.out.println(">>> Filtro recebido: '" + filtro + "'");

        // Começo da query com JOIN
        String sql = "SELECT * FROM agendar_medicamento am " +
                "JOIN animal a ON a.ani_id = am.agemed_animal_id";

        // Se tiver filtro, filtra pelo nome do animal
        if (filtro != null && !filtro.trim().isEmpty()) {
            sql += " WHERE LOWER(a.ani_nome) LIKE LOWER('%" + filtro.trim() + "%')";
        }
        //System.out.println(">>> SQL gerado: " + sql);

        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new AgendarMedicamento(
                        resultSet.getInt("agemed_id"),
                        resultSet.getInt("agemed_medicamento_id"),
                        resultSet.getInt("agemed_animal_id"),
                        resultSet.getString("agemed_dataAplicacao"),
                        resultSet.getBoolean("agemed_status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<AgendarMedicamento> getIdAnimal(int animalId, Conexao conexao) {
        List<AgendarMedicamento> lista = new ArrayList<>();

        //System.out.println(">>> Filtro recebido: '" + filtro + "'");

        // Começo da query com JOIN
        String sql = "SELECT * FROM agendar_medicamento WHERE agemed_animal_id ="+animalId+ " AND agemed_status = false";

        //System.out.println(">>> SQL gerado: " + sql);

        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new AgendarMedicamento(
                        resultSet.getInt("agemed_id"),
                        resultSet.getInt("agemed_medicamento_id"),
                        resultSet.getInt("agemed_animal_id"),
                        resultSet.getString("agemed_dataAplicacao"),
                        resultSet.getBoolean("agemed_status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}
