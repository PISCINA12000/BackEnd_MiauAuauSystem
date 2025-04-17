package miau.auau.amigosdequatropatas.dao;
import miau.auau.amigosdequatropatas.entities.Adocao;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdocaoDAO implements IDAL<Adocao> {

    @Override
    public boolean gravar(Adocao entidade, Conexao conexao) {
        String sql = """
                INSERT INTO adocao (ado_animal_id, ado_usuario_id, ado_data)
                VALUES (#1, #2, '#3')
                """;
        sql = sql.replace("#1", "" + entidade.getAnimal().getCodAnimal())
                .replace("#2", "" + entidade.getUsuario().getCod())
                .replace("#3", entidade.getData());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Adocao entidade, Conexao conexao) {
        String sql = """
                UPDATE adocao
                SET ado_animal_id = #1, ado_usuario_id = #2, ado_data = '#3'
                WHERE ado_animal_id = #1 AND ado_usuario_id = #2
                """;
        sql = sql.replace("#1", "" + entidade.getAnimal().getCodAnimal())
                .replace("#2", "" + entidade.getUsuario().getCod())
                .replace("#3", entidade.getData());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(Adocao entidade, Conexao conexao) {
        String sql = "DELETE FROM adocao WHERE ado_animal_id = " + entidade.getAnimal().getCodAnimal() +
                " AND ado_usuario_id = " + entidade.getUsuario().getCod();
        return conexao.manipular(sql);
    }

    @Override
    public Adocao get(int id, Conexao conexao) {
        Adocao adocao = null;
        String sql = "SELECT * FROM adocao WHERE ado_animal_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                adocao = new Adocao(
                        new AnimalDAO().get(resultSet.getInt("ado_animal_id"), conexao),
                        new UsuarioDAO().get(resultSet.getInt("ado_usuario_id"), conexao),
                        resultSet.getString("ado_data")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adocao;
    }

    @Override
    public List<Adocao> get(String filtro, Conexao conexao) {
        List<Adocao> lista = new ArrayList<>();
        String sql = "SELECT * FROM adocao";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            sql += " WHERE ado_data = '" + filtro + "'";
        }
        sql += " ORDER BY ado_data";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Adocao(
                        new AnimalDAO().get(resultSet.getInt("ado_animal_id"), conexao),
                        new UsuarioDAO().get(resultSet.getInt("ado_usuario_id"), conexao),
                        resultSet.getString("ado_data")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
