package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.PlanoContasGerencial;
import miau.auau.amigosdequatropatas.entities.PlanoContasReferencial;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanoContasReferencialDAO implements IDAL<PlanoContasReferencial> {
    @Override
    public List<PlanoContasReferencial> get(String filtro, Conexao conexao) {
        List<PlanoContasReferencial> lista = new ArrayList<>();
        String sql = "SELECT * FROM plano_contas_referencial";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            //filtro = "'" + filtro + "'";
            //sql += " WHERE tpp_descricao = " + filtro;
            sql += " WHERE pcr_descricao ILIKE '%" + filtro + "%'";
        }
        sql += " ORDER BY pcr_descricao";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new PlanoContasReferencial(
                        resultSet.getInt("pcr_id"),
                        resultSet.getString("pcr_descricao"),
                        resultSet.getString("pcr_natureza"),
                        resultSet.getString("pcr_classificacao")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public PlanoContasReferencial get(int id, Conexao conexao) {
        PlanoContasReferencial planoContasReferencial = null;
        String sql = "SELECT * FROM plano_contas_referencial WHERE pcr_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                planoContasReferencial = new PlanoContasReferencial(
                        id,
                        resultSet.getString("pcr_descricao"),
                        resultSet.getString("pcr_natureza"),
                        resultSet.getString("pcr_classificacao")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planoContasReferencial;
    }

    @Override
    public boolean gravar(PlanoContasReferencial entidade, Conexao conexao) {
        String sql = """
                INSERT INTO plano_contas_referencial (pcr_descricao, pcr_natureza, pcr_classificacao)
                VALUES ('#1', '#2', '#3')
                """;
        sql = sql.replace("#1", entidade.getDescricao())
                .replace("#2", entidade.getNatureza())
                .replace("#3", entidade.getClassificacao());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(PlanoContasReferencial entidade, Conexao conexao) {
        String sql = "DELETE FROM plano_contas_referencial WHERE pcr_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(PlanoContasReferencial entidade, Conexao conexao) {
        String sql = """
                UPDATE plano_contas_referencial
                SET pcr_descricao = '#1', pcr_natureza = '#2', pcr_classificacao = '#3'
                WHERE pcr_id = #4
                """;
        sql = sql.replace("#1", entidade.getDescricao())
                .replace("#2", entidade.getNatureza())
                .replace("#3", entidade.getClassificacao())
                .replace("#4", "" + entidade.getCod());
        return conexao.manipular(sql);
    }
}
