package miau.auau.amigosdequatropatas.dao;
import miau.auau.amigosdequatropatas.entities.AgendarMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgendarMedicamentoDAO implements IDAL<AgendarMedicamento> {

    @Override
    public boolean gravar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = """
                INSERT INTO agendar_medicamento (agemed_medicamento_id, agemed_animal_id, agemed_dataAplicacao, agemed_status)
                VALUES (#1, #2, '#3', #4)
                """;
        sql = sql.replace("#1", "" + entidade.getMedicamento().getCod())
                .replace("#2", "" + entidade.getAnimal().getCodAnimal())
                .replace("#3", entidade.getDataAplicacao())
                .replace("#4", entidade.getStatus().toString()); // Adiciona o status

        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = """
            UPDATE agendar_medicamento
            SET agemed_medicamento_id = #1, agemed_animal_id = #2, agemed_dataAplicacao = '#3', agemed_status = #5
            WHERE agemed_id = #4
            """;
        sql = sql.replace("#1","" + entidade.getMedicamento().getCod())
                .replace("#2","" + entidade.getAnimal().getCodAnimal())
                .replace("#3", entidade.getDataAplicacao())
                .replace("#4", "" + entidade.getCodAgendarMedicamento())
                .replace("#5", entidade.getStatus().toString()); // Adiciona o status na atualização

        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(AgendarMedicamento entidade, Conexao conexao) {
        String sql = "DELETE FROM agendar_medicamento WHERE agemed_id = " + entidade.getCodAgendarMedicamento();
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
                        resultSet.getInt("agemed_id"),
                        new TipoMedicamentoDAO().get(resultSet.getInt("agemed_medicamento_id"), conexao),
                        new AnimalDAO().get(resultSet.getInt("agemed_animal_id"), conexao),
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
        String sql = "SELECT * FROM agendar_medicamento";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            sql += " WHERE agemed_animal_id = '" + filtro + "'";
        }
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new AgendarMedicamento(
                        resultSet.getInt("agemed_id"),
                        new TipoMedicamentoDAO().get(resultSet.getInt("agemed_medicamento_id"), conexao),
                        new AnimalDAO().get(resultSet.getInt("agemed_animal_id"), conexao),
                        resultSet.getString("agemed_dataAplicacao"),
                        resultSet.getBoolean("agemed_status") // Recupera o status do banco
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
