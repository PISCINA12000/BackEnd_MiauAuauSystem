package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Adocao;
import miau.auau.amigosdequatropatas.entities.Doacao;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO implements IDAL<Doacao>
{
    @Override
    public boolean gravar(Doacao entidade, Conexao conexao) {
        String sql = """
                INSERT INTO doacao (doa_usuario_id, doa_status, doa_data, doa_valor)
                VALUES (#1, '#2', '#3', #4)
                """;
        sql = sql.replace("#1", "" + entidade.getUsuario().getCod())
                .replace("#2", entidade.getStatus())
                .replace("#3", entidade.getData())
                .replace("#4", entidade.getValor());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Doacao entidade, Conexao conexao) {
        String sql = """
            UPDATE doacao
            SET doa_usuario_id = #1, doa_status = '#2', doa_data = '#3', doa_valor = '#4'
            WHERE ado_id = #5
            """;
        sql = sql.replace("#1", ""+entidade.getUsuario().getCod())
                .replace("#2", entidade.getStatus())
                .replace("#3", entidade.getData())
                .replace("#4", entidade.getValor())
                .replace("#5", ""+entidade.getCodDoacao());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(Doacao entidade, Conexao conexao) {
        String sql = "DELETE FROM doacao WHERE doa_id = " + entidade.getCodDoacao();
        return conexao.manipular(sql);
    }

    @Override
    public Doacao get(int id, Conexao conexao) {
        Doacao doacao = null;
        String sql = "SELECT * FROM doacao WHERE doa_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                doacao = new Doacao(
                        resultSet.getInt("doa_id"),
                        new UsuarioDAO().get(resultSet.getInt("doa_usuario_id"), conexao),
                        resultSet.getString("doa_status"),
                        resultSet.getString("doa_data"),
                        resultSet.getInt("doa_valor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doacao;
    }

    @Override
    public List<Doacao> get(String filtro, Conexao conexao) {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM doacao";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            sql += " WHERE TO_CHAR(doa_data, 'YYYY') = '"+filtro+"'";
        }
        sql += " ORDER BY doa_data";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Doacao(
                        resultSet.getInt("doa_id"),
                        new UsuarioDAO().get(resultSet.getInt("doa_usuario_id"), conexao),
                        resultSet.getString("ado_status"),
                        resultSet.getString("ado_data"),
                        resultSet.getInt("doa_valor")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
