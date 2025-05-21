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
                INSERT INTO adocao (ado_animal_id, ado_usuario_id, ado_data, ado_status)
                VALUES (#1, #2, '#3', '#4')
                """;
        sql = sql.replace("#1", "" + entidade.getAnimal().getCodAnimal())
                .replace("#2", "" + entidade.getUsuario().getCod())
                .replace("#3", entidade.getData())
                .replace("#4", entidade.getStatus());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Adocao entidade, Conexao conexao) {
        String sql = """
            UPDATE adocao
            SET ado_animal_id = #1, ado_usuario_id = #2, ado_data = '#3', ado_status = '#4'
            WHERE ado_id = #5
            """;
        sql = sql.replace("#1", ""+entidade.getAnimal().getCodAnimal())
                .replace("#2", ""+entidade.getUsuario().getCod())
                .replace("#3", entidade.getData())
                .replace("#4", entidade.getStatus())
                .replace("#5", ""+entidade.getCodAdocao());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(Adocao entidade, Conexao conexao) {
        String sql = "DELETE FROM adocao WHERE ado_id = " + entidade.getCodAdocao();
        return conexao.manipular(sql);
    }

    @Override
    public Adocao get(int id, Conexao conexao) {
        Adocao adocao = null;
        String sql = "SELECT * FROM adocao WHERE ado_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                adocao = new Adocao(
                        resultSet.getInt("ado_id"),
                        new AnimalDAO().get(resultSet.getInt("ado_animal_id"), conexao),
                        new UsuarioDAO().get(resultSet.getInt("ado_usuario_id"), conexao),
                        resultSet.getString("ado_data"),
                        resultSet.getString("ado_status")
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
        String filtroAno = "";
        String filtroStatus = "";
        String sql = "SELECT * FROM adocao";
        System.out.println("filtro recebido"+filtro);
        if (!filtro.isEmpty() && !filtro.equals(" "))
        {
            int i = 0;
            while (i < filtro.length() && filtro.charAt(i) != ' ')
            {
                filtroAno += filtro.charAt(i);
                i++;
            }
            i++;
            while (i < filtro.length())
            {
                System.out.println("entrei");
                System.out.println(filtro.charAt(i));
                filtroStatus += filtro.charAt(i);
                i++;
            }

            if (filtroAno.isEmpty() && !filtroStatus.isEmpty())
            {
                System.out.println(filtroStatus);
                sql += " WHERE ado_status = '"+filtroStatus+"'";
            }
            else
            if (!filtroAno.isEmpty() && filtroStatus.isEmpty())
            {
                System.out.println(filtroAno);
                sql += " WHERE TO_CHAR(ado_data, 'YYYY') = '"+filtroAno+"'";
            }
            else
            if (!filtroAno.isEmpty() && !filtroStatus.isEmpty())
            {
                sql += " WHERE TO_CHAR(ado_data, 'YYYY') = '"+filtroAno+"'" + " AND ado_status = '"+filtroStatus+"'";
            }
        }

        sql += " ORDER BY ado_data";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Adocao(
                        resultSet.getInt("ado_id"),
                        new AnimalDAO().get(resultSet.getInt("ado_animal_id"), conexao),
                        new UsuarioDAO().get(resultSet.getInt("ado_usuario_id"), conexao),
                        resultSet.getString("ado_data"),
                        resultSet.getString("ado_status")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String > getAnos(Conexao conexao) {
        List<String> lista = new ArrayList<>();
        String sql;
        sql = "SELECT DISTINCT TO_CHAR(ado_data, 'YYYY') FROM adocao ORDER BY TO_CHAR(ado_data, 'YYYY')";
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Adocao> getAdocaoPeloUsuId(int id, String filtro, Conexao conexao) {
        List<Adocao> list = new ArrayList();
        String sql = "SELECT * FROM adocao JOIN animal on ado_animal_id = ani_id WHERE ado_usuario_id = "+id;
        if (!filtro.isEmpty() && !filtro.equals(" "))
        {
            sql += " and ani_nome ILIKE '%" + filtro + "%'";
        }
        sql += " ORDER BY ani_nome";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()){

                list.add(new Adocao(
                    resultSet.getInt("ado_id"),
                    new AnimalDAO().get(resultSet.getInt("ado_animal_id"), conexao),
                    new UsuarioDAO().get(resultSet.getInt("ado_usuario_id"), conexao),
                    resultSet.getString("ado_data"),
                    resultSet.getString("ado_status")));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
