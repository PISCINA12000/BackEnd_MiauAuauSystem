package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.TipoPagamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class TipoPagamentoDAO implements IDAL<TipoPagamento> {
    @Override
    public List<TipoPagamento> get(String filtro, Conexao conexao) {
        List<TipoPagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_pagamento";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            filtro = "'" + filtro + "'";
            sql += " WHERE tpp_descricao = " + filtro;
        }
        sql += " ORDER BY tpp_descricao";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new TipoPagamento(
                        resultSet.getInt("tpp_id"),
                        resultSet.getString("tpp_descricao")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public TipoPagamento get(int id, Conexao conexao) {
        TipoPagamento tipoPagamento = null;
        String sql = "SELECT * FROM tipo_pagamento WHERE tpp_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                tipoPagamento = new TipoPagamento(
                        id,
                        resultSet.getString("tpp_descricao")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoPagamento;
    }

    @Override
    public boolean gravar(TipoPagamento entidade, Conexao conexao) {
        String sql = """
                INSERT INTO tipo_pagamento (tpp_descricao)
                VALUES ('#1')
                """;
        sql = sql.replace("#1", entidade.getDescricao());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(TipoPagamento entidade, Conexao conexao) {
        String sql = "DELETE FROM tipo_pagamento WHERE tpp_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(TipoPagamento entidade, Conexao conexao) {
        String sql = """
                UPDATE tipo_pagamento
                SET tpp_descricao = '#1'
                WHERE tpp_id = #2
                """;
        sql = sql.replace("#1", entidade.getDescricao())
                .replace("#2", "" + entidade.getCod());
        return conexao.manipular(sql);
    }
}
