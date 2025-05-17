package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDAO implements IDAL<Usuario> {

    @Override
    public boolean gravar(Usuario entidade, Conexao conexao) {
        String sql = """
            INSERT INTO usuario (
                usu_nome, usu_email, usu_senha, usu_telefone, usu_cpf, 
                usu_privilegio, usu_sexo, usu_cep, usu_rua, usu_bairro, usu_numero, usu_cidade, usu_estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setString(1, entidade.getNome());
            stmt.setString(2, entidade.getEmail());
            stmt.setString(3, entidade.getSenha());
            stmt.setString(4, entidade.getTelefone());
            stmt.setString(5, entidade.getCpf());
            stmt.setString(6, entidade.getPrivilegio());
            stmt.setString(7, entidade.getSexo());
            stmt.setString(8, entidade.getCep());
            stmt.setString(9, entidade.getRua());
            stmt.setString(10, entidade.getBairro());
            stmt.setString(11, entidade.getNumero());
            stmt.setString(12, entidade.getCidade());
            stmt.setString(13, entidade.getEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // ou logue o erro
            return false;
        }
    }

    @Override
    public boolean alterar(Usuario entidade, Conexao conexao) {
        String sql = """
            UPDATE usuario
            SET usu_nome = ?, usu_email = ?, usu_senha = ?, usu_telefone = ?, usu_cpf = ?, 
                usu_privilegio = ?, usu_sexo = ?, usu_cep = ?, usu_rua = ?, usu_bairro = ?, usu_numero = ?,
                usu_cidade = ?, usu_estado = ?
            WHERE usu_id = ?
        """;

        try (PreparedStatement ps = conexao.getPreparedStatement(sql)) {
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getEmail());
            ps.setString(3, entidade.getSenha());
            ps.setString(4, entidade.getTelefone());
            ps.setString(5, entidade.getCpf());
            ps.setString(6, entidade.getPrivilegio());
            ps.setString(7, entidade.getSexo());
            ps.setString(8, entidade.getCep());
            ps.setString(9, entidade.getRua());
            ps.setString(10, entidade.getBairro());
            ps.setString(11, entidade.getNumero());
            ps.setString(12, entidade.getCidade());
            ps.setString(13, entidade.getEstado());
            ps.setInt(14, entidade.getCod());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // ou logue como preferir
            return false;
        }
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
                        resultSet.getString("usu_numero"),
                        resultSet.getString("usu_cidade"),
                        resultSet.getString("usu_estado")
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
            //filtro = "'" + filtro + "'";
            //sql += " WHERE usu_nome = " + filtro;
            sql += " WHERE usu_nome ILIKE '%" + filtro + "%'";
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
                        resultSet.getString("usu_numero"),
                        resultSet.getString("usu_cidade"),
                        resultSet.getString("usu_estado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario getEmail(String filtro, Conexao conexao)
    {
        Usuario usuario = null;
        String sql;
        sql = "SELECT * FROM usuario WHERE usu_email = '" + filtro + "'";
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                usuario = new Usuario(
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
                        resultSet.getString("usu_numero"),
                        resultSet.getString("usu_cidade"),
                        resultSet.getString("usu_estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    public Usuario getCPF(String filtro, Conexao conexao)
    {
        Usuario usuario = null;
        String sql;
        sql = "SELECT * FROM usuario WHERE usu_cpf = '" + filtro + "'";
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                usuario = new Usuario(
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
                        resultSet.getString("usu_numero"),
                        resultSet.getString("usu_cidade"),
                        resultSet.getString("usu_estado")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
