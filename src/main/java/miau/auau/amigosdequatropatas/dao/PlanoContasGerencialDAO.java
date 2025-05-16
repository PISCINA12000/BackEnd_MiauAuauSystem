package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.PlanoContasGerencial;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlanoContasGerencialDAO implements IDAL<PlanoContasGerencial> {
    @Override
    public List<PlanoContasGerencial> get(String filtro, Conexao conexao) {
        List<PlanoContasGerencial> lista = new ArrayList<>();
        String sql = "SELECT * FROM plano_contas_gerencial";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            //filtro = "'" + filtro + "'";
            //sql += " WHERE tpp_descricao = " + filtro;
            sql += " WHERE pcg_descricao ILIKE '%" + filtro + "%'";
        }
        sql += " ORDER BY pcg_descricao";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new PlanoContasGerencial(
                        resultSet.getInt("pcg_id"),
                        resultSet.getString("pcg_descricao"),
                        resultSet.getInt(("pcr_id"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public PlanoContasGerencial get(int id, Conexao conexao) {
        PlanoContasGerencial planoContasGerencial = null;
        String sql = "SELECT * FROM plano_contas_gerencial WHERE pcg_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                planoContasGerencial = new PlanoContasGerencial(
                        id,
                        resultSet.getString("pcg_descricao"),
                        resultSet.getInt("pcr_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planoContasGerencial;
    }

    @Override
    public boolean gravar(PlanoContasGerencial entidade, Conexao conexao) {
        String sql = """
                INSERT INTO plano_contas_gerencial (pcg_descricao, pcr_id)
                VALUES ('#1', #2)
                """;
        sql = sql.replace("#1", entidade.getDescricao())
                .replace("#2", "" + entidade.getCodPcr());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(PlanoContasGerencial entidade, Conexao conexao) {
        String sql = "DELETE FROM plano_contas_gerencial WHERE pcg_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(PlanoContasGerencial entidade, Conexao conexao) {
        String sql = """
                UPDATE plano_contas_gerencial
                SET pcg_descricao = '#1', pcr_id = #2
                WHERE pcg_id = #3
                """;
        sql = sql.replace("#1", entidade.getDescricao())
                .replace("#2", "" + entidade.getCodPcr())
                .replace("#3", "" + entidade.getCod());
        return conexao.manipular(sql);
    }
}
