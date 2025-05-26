package miau.auau.amigosdequatropatas.dao;


import miau.auau.amigosdequatropatas.entities.Prontuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
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
public class ProntuarioDAO implements IDAL<Prontuario> {

    @Override
    public boolean gravar(Prontuario entidade, Conexao conexao) {
        String sql = """
                INSERT INTO prontuario (
                    pron_animal_id, pron_data, pron_tipoRegistro, pron_observacao, pron_documento
                ) VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {


            //stmt.setInt(1, entidade.getCod());

            stmt.setInt(1, entidade.getCodAnimal());

            // Converte a String de data para o formato de Date do SQL
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(entidade.getData());
            stmt.setDate(2, new Date(parsedDate.getTime()));
            //stmt.setDate(3, Date.valueOf(entidade.getData()));

            stmt.setString(3, entidade.getTipoRegistro());
            stmt.setString(4, entidade.getObservacao());

            //para caso eu não envie pdf algum
            if (entidade.getPDF() == null)
                stmt.setNull(5, java.sql.Types.BINARY);
            else
                stmt.setBytes(5, entidade.getPDF());

            return stmt.executeUpdate() > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace(); // ou logue o erro
            return false;
        }

    }


    @Override
    public boolean alterar(Prontuario entidade, Conexao conexao) {
        //pron_id, pron_animal_id, pron_data, pron_tipoRegistro, pron_observacao, pron_documento
        String sql = """
                    UPDATE prontuario
                    SET pron_animal_id = ?, pron_data = ?, pron_tipoRegistro = ?, pron_observacao = ?, 
                        pron_documento = ?
                    WHERE pron_id = ?
                """;

        try (PreparedStatement ps = conexao.getPreparedStatement(sql)) {

            ps.setInt(1, entidade.getCodAnimal());

            // Converte a String de data para o formato de Date do SQL
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(entidade.getData());
            ps.setDate(2, new Date(parsedDate.getTime()));
            //ps.setDate(3, Date.valueOf(entidade.getData()));


            ps.setString(3, entidade.getTipoRegistro());
            ps.setString(4, entidade.getObservacao());
            //para caso eu não envie pdf algum
            if (entidade.getPDF() == null)
                ps.setNull(5, java.sql.Types.BINARY);
            else
                ps.setBytes(5, entidade.getPDF());

            ps.setInt(6, entidade.getCod());

            return ps.executeUpdate() > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace(); // ou logue como preferir
            return false;
        }
    }


    @Override
    public boolean apagar(Prontuario entidade, Conexao conexao) {
        String sql = "DELETE FROM prontuario WHERE pron_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }


    //buscar registro do prontuario(unica linha)
    @Override
    public Prontuario get(int id, Conexao conexao) {
        Prontuario prontuario = null;
        String sql = "SELECT * FROM prontuario WHERE pron_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                prontuario = new Prontuario(
                        resultSet.getInt("pron_id"),
                        resultSet.getInt("pron_animal_id"),
                        resultSet.getDate("pron_data").toString(),
                        resultSet.getString("pron_tipoRegistro"),
                        resultSet.getString("pron_observacao"),
                        resultSet.getBytes("pron_documento"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prontuario;
    }

    //filtrar registro pelo tipo do registro ou nao e ordena pela data com filtro ou n
    @Override
    public List<Prontuario> get(String filtro, Conexao conexao) {
        List<Prontuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM prontuario";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            sql += " WHERE pron_tipoRegistro ILIKE '%" + filtro + "%'";
        }
        sql += " ORDER BY pron_data";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Prontuario(
                        resultSet.getInt("pron_id"),
                        resultSet.getInt("pron_animal_id"),
                        resultSet.getDate("pron_data").toString(),
                        resultSet.getString("pron_tipoRegistro"),
                        resultSet.getString("pron_observacao"),
                        resultSet.getBytes("pron_documento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    //busca pelo ID do animal(pode ser varias linhas)
    public List<Prontuario> getIdAnimal(int id, Conexao conexao) {
        List<Prontuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM prontuario WHERE pron_animal_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Prontuario(
                        resultSet.getInt("pron_id"),
                        resultSet.getInt("pron_animal_id"),
                        resultSet.getDate("pron_data").toString(),
                        resultSet.getString("pron_tipoRegistro"),
                        resultSet.getString("pron_observacao"),
                        resultSet.getBytes("pron_documento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public List<Prontuario> getByData(String dataInicial, String dataFinal, Conexao conexao) {
        List<Prontuario> lista = new ArrayList<>();
        // Converter dataInicial e dataFinal para o formato AAAA-MM-DD
        String[] partesDataInicial = dataInicial.split("/");
        String dataInicialFormatada = partesDataInicial[2] + "-" + partesDataInicial[1] + "-" + partesDataInicial[0];

        String[] partesDataFinal = dataFinal.split("/");
        String dataFinalFormatada = partesDataFinal[2] + "-" + partesDataFinal[1] + "-" + partesDataFinal[0];

        // Montar o comando SQL com as datas formatadas corretamente
        String sql = """
                SELECT * FROM prontuario
                WHERE pron_data BETWEEN '#1' AND '#2'
            """;
        sql = sql.replace("#1", dataInicialFormatada)
                .replace("#2", dataFinalFormatada);

        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Prontuario(
                        resultSet.getInt("pron_id"),
                        resultSet.getInt("pron_animal_id"),
                        resultSet.getDate("pron_data").toString(),
                        resultSet.getString("pron_tipoRegistro"),
                        resultSet.getString("pron_observacao"),
                        resultSet.getBytes("pron_documento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
