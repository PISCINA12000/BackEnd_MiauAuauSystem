package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Doacao;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.IDAL;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoacaoDAO implements IDAL<Doacao> {

    @Override
    public boolean gravar(Doacao entidade, Conexao conexao) {
        String sql = """
                INSERT INTO doacao (doa_usuario_id, doa_status, doa_data, doa_valor)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1, entidade.getUsuario().getCod());
            stmt.setString(2, entidade.getStatus());

            // Convertendo data (String -> Date)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataFormatada = sdf.parse(entidade.getData());
            stmt.setDate(3, new Date(dataFormatada.getTime()));

            stmt.setInt(4, entidade.getValor());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean alterar(Doacao entidade, Conexao conexao) {
        String sql = """
                UPDATE doacao
                SET doa_usuario_id = ?, doa_status = ?, doa_data = ?, doa_valor = ?
                WHERE doa_id = ?
                """;
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1, entidade.getUsuario().getCod());
            stmt.setString(2, entidade.getStatus());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataFormatada = sdf.parse(entidade.getData());
            stmt.setDate(3, new Date(dataFormatada.getTime()));

            stmt.setInt(4, entidade.getValor());
            stmt.setInt(5, entidade.getCodDoacao());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean apagar(Doacao entidade, Conexao conexao) {
        String sql = "DELETE FROM doacao WHERE doa_id = ?";
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1, entidade.getCodDoacao());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Doacao get(int id, Conexao conexao) {
        Doacao doacao = null;
        String sql = "SELECT * FROM doacao WHERE doa_id = ?";
        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                doacao = new Doacao(
                        rs.getInt("doa_id"),
                        new UsuarioDAO().get(rs.getInt("doa_usuario_id"), conexao),
                        rs.getString("doa_status"),
                        rs.getDate("doa_data").toString(),
                        rs.getInt("doa_valor")
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
        if (filtro != null && !filtro.isBlank()) {
            sql += " WHERE TO_CHAR(doa_data, 'YYYY') = ?";
        }
        sql += " ORDER BY doa_data";

        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            if (filtro != null && !filtro.isBlank()) {
                stmt.setString(1, filtro);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Doacao(
                        rs.getInt("doa_id"),
                        new UsuarioDAO().get(rs.getInt("doa_usuario_id"), conexao),
                        rs.getString("doa_status"),
                        rs.getDate("doa_data").toString(),
                        rs.getInt("doa_valor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
