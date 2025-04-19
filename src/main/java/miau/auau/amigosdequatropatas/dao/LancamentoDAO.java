package miau.auau.amigosdequatropatas.dao;

import miau.auau.amigosdequatropatas.entities.Lancamento;
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
public class LancamentoDAO implements IDAL<Lancamento> {

    @Override
    public boolean gravar(Lancamento entidade, Conexao conexao) {
        String sql = """
            INSERT INTO lancamento (
                lan_codTpLanc, lan_codAnimal, lan_data, lan_debito, lan_credito, 
                lan_descricao, lan_valor, lan_pdf
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement stmt = conexao.getPreparedStatement(sql)) {
            stmt.setString(1, "" + entidade.getCodTpLanc());
            stmt.setString(2, "" + entidade.getCodAnimal());
            stmt.setString(3, entidade.getData());
            stmt.setString(4, "" + entidade.getDebito());
            stmt.setString(5, "" + entidade.getCredito());
            stmt.setString(6, entidade.getDescricao());
            stmt.setString(7, "" + entidade.getValor());
            stmt.setBytes(8, entidade.getPDF());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // ou logue o erro
            return false;
        }
    }

    @Override
    public boolean alterar(Lancamento entidade, Conexao conexao) {
        String sql = """
            UPDATE lancamento
            SET lan_codTpLanc = ?, lan_codAnimal = ?, lan_data = ?, lan_debito = ?, lan_credito = ?, 
                lan_descricao = ?, lan_valor = ?, lan_pdf = ?
            WHERE lan_id = ?
        """;

        try (PreparedStatement ps = conexao.getPreparedStatement(sql)) {
            ps.setString(1, "" + entidade.getCodTpLanc());
            ps.setString(2, "" + entidade.getCodAnimal());
            ps.setString(3, entidade.getData());
            ps.setString(4, "" + entidade.getDebito());
            ps.setString(5, "" + entidade.getCredito());
            ps.setString(6, entidade.getDescricao());
            ps.setString(7, "" + entidade.getValor());
            ps.setBytes(8, entidade.getPDF());
            ps.setInt(9, entidade.getCod());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // ou logue como preferir
            return false;
        }
    }

    @Override
    public boolean apagar(Lancamento entidade, Conexao conexao) {
        String sql = "DELETE FROM lancamento WHERE lan_id = " + entidade.getCod();
        return conexao.manipular(sql);
    }

    @Override
    public Lancamento get(int id, Conexao conexao) {
        Lancamento lancamento = null;
        String sql = "SELECT * FROM lancamento WHERE lan_id = " + id;
        ResultSet resultSet = conexao.consultar(sql);
        try {
            if (resultSet.next()) {
                lancamento = new Lancamento(
                        id,
                        resultSet.getString("lan_data"),
                        Integer.parseInt(resultSet.getString("lan_codTpLanc")),
                        Integer.parseInt(resultSet.getString("lan_codAnimal")),
                        Integer.parseInt(resultSet.getString("lan_debito")),
                        Integer.parseInt(resultSet.getString("lan_credito")),
                        resultSet.getString("lan_descricao"),
                        Double.parseDouble(resultSet.getString("lan_valor")),
                        resultSet.getString("lan_pdf").getBytes()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lancamento;
    }

    @Override
    public List<Lancamento> get(String filtro, Conexao conexao) {
        List<Lancamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM lancamento";
        if (!filtro.isEmpty() && !filtro.equals(" ")) {
            filtro = "'" + filtro + "'";
            sql += " WHERE lan_nome = " + filtro;
        }
        sql += " ORDER BY lan_nome";
        System.out.println("SQL gerado: " + sql);
        ResultSet resultSet = conexao.consultar(sql);
        try {
            while (resultSet.next()) {
                lista.add(new Lancamento(
                        resultSet.getInt("lan_id"),
                        resultSet.getString("lan_data"),
                        resultSet.getInt("lan_codTpLanc"),
                        resultSet.getInt("lan_codAnimal"),
                        resultSet.getInt("lan_debito"),
                        resultSet.getInt("lan_credito"),
                        resultSet.getString("lan_descricao"),
                        resultSet.getDouble("lan_valor"),
                        resultSet.getBytes("lan_pdf")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
