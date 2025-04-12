package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDAO implements IDAL<Usuario> {

    @Override
    public boolean gravar(Usuario entidade, Conexao conexao) {
        String sql = """
                INSERT INTO usuario (usu_nome, usu_email, usu_senha, usu_telefone, usu_cpf, usu_privilegio, 
                usu_sexo, usu_cep, usu_rua, usu_bairro, usu_numero)
                VALUES ('#1', '#2', '#3', '#4', '#5', '#6', '#7', '#8', '#9', '#10', '#11')
                """;
        sql = sql.replace("#1", entidade.getNome())
                .replace("#2", entidade.getEmail())
                .replace("#3", entidade.getSenha())
                .replace("#4", entidade.getTelefone())
                .replace("#5", entidade.getCpf())
                .replace("#6", entidade.getPrivilegio())
                .replace("#7", entidade.getSexo())
                .replace("#8", entidade.getCep())
                .replace("#9", entidade.getRua())
                .replace("#10", entidade.getBairro())
                .replace("#11", entidade.getNumero());
        return conexao.manipular(sql);
    }

    @Override
    public boolean alterar(Usuario entidade, Conexao conexao) {
        String sql = """
                UPDATE usuario
                SET usu_nome = '#1', usu_email = '#2', usu_senha = '#3', usu_telefone = '#4', usu_cpf = '#5', 
                usu_privilegio = '#6', usu_sexo = '#7', usu_cep = '#8', usu_rua = '#9', usu_bairro = '#10', 
                usu_numero = '#11'
                WHERE usu_id = #12
                """;
        sql = sql.replace("#1", entidade.getNome())
                .replace("#2", entidade.getEmail())
                .replace("#3", entidade.getSenha())
                .replace("#4", entidade.getTelefone())
                .replace("#5", entidade.getCpf())
                .replace("#6", entidade.getPrivilegio())
                .replace("#7", entidade.getSexo())
                .replace("#8", entidade.getCep())
                .replace("#9", entidade.getRua())
                .replace("#10", entidade.getBairro())
                .replace("#11", entidade.getNumero())
                .replace("#12", "" + entidade.getCod());
        return conexao.manipular(sql);
    }

    @Override
    public boolean apagar(Usuario entidade, Conexao conexao) {
        String sql = "DELETE FROM usuario WHERE usu_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }

    @Override
    public Usuario get(int id, Conexao conexao) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE usu_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                usuario = new Usuario(
                        id,
                        resultSet.getString("usu_nome"),
                        resultSet.getString("usu_email"),
                        resultSet.getString("usu_senha"),
                        resultSet.getString("usu_telefone"),
                        resultSet.getString("usu_cpf"),
                        resultSet.getString("usu_privilegio"),
                        resultSet.getString("usu_sexo"),
                        resultSet.getString("usu_cep"),
                        resultSet.getString("usu_rua"),
                        resultSet.getString("usu_bairro"),
                        resultSet.getString("usu_numero")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> get(String filtro, Conexao conexao) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            filtro = "'" + filtro + "'";
            sql += " WHERE usu_nome = " + filtro;
        }
        sql += " ORDER BY usu_nome";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Usuario(
                        resultSet.getInt("usu_id"),
                        resultSet.getString("usu_nome"),
                        resultSet.getString("usu_email"),
                        resultSet.getString("usu_senha"),
                        resultSet.getString("usu_telefone"),
                        resultSet.getString("usu_cpf"),
                        resultSet.getString("usu_privilegio"),
                        resultSet.getString("usu_sexo"),
                        resultSet.getString("usu_cep"),
                        resultSet.getString("usu_rua"),
                        resultSet.getString("usu_bairro"),
                        resultSet.getString("usu_numero")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
