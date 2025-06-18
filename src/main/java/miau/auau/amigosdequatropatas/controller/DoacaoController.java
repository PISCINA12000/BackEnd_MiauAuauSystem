package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.dao.DoacaoDAO;
import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.entities.Doacao;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoacaoController {
    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            Doacao doacao = new Doacao();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario;

            //usuario = usuario.consultarID(Integer.parseInt(json.get("usuario").toString()),conexao);

            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            String status = json.get("status").toString();
            String data = json.get("data").toString();
            int valor = ((int) json.get("valor"));

            doacao.setData(data);
            doacao.setStatus(status);
            doacao.setValor(valor);
            doacao.setUsuario(usuario);

            return doacao.incluir(conexao);
            // rollback; finalizar t. desconecta;
        }
        //se chegou até aqui é porque algo deu errado
        return false;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //criando a lista que conterá os JSON's
        Doacao doacao = new Doacao();
        List<Doacao> lista = doacao.consultar(filtro, conexao);

        //verificação de a minha lista JSON está vazia ou não
        if (lista != null) {
            //crio uma lista json contendo os animais que retornaram no meu consultar
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {

                Doacao a = lista.get(i);
                // dados animal
                Map<String, Object> jsonUsuario = new HashMap<>();
                jsonUsuario.put("cod", a.getUsuario().getCod());
                jsonUsuario.put("nome", a.getUsuario().getNome());
                jsonUsuario.put("email", a.getUsuario().getEmail());
                jsonUsuario.put("telefone", a.getUsuario().getTelefone());

                Map<String, Object> jsonDoacao = new HashMap<>();
                jsonDoacao.put("codDoacao", a.getCodDoacao());
                jsonDoacao.put("status", a.getStatus());
                jsonDoacao.put("data", a.getData());
                jsonDoacao.put("valor", a.getValor());
                jsonDoacao.put("usuario", jsonUsuario);
                listaJson.add(jsonDoacao);
            }
            return listaJson;
        } else
            return null;
    }

    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Doacao doacao = new Doacao();
        doacao = doacao.consultarID(id, conexao);
        // Se a adocao for encontrada, exclui; caso contrário, retorna false
        if (doacao != null)
            return doacao.excluir(conexao);
        return false;
    }


    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Doacao doacao = new Doacao();
        doacao = doacao.consultarID(id, conexao);

        // Retornando os dados da adocao, se encontrado
        if (doacao != null) {

            Map<String, Object> jsonUsuario = new HashMap<>();
            jsonUsuario.put("cod", doacao.getUsuario().getCod());
            jsonUsuario.put("nome", doacao.getUsuario().getNome());
            jsonUsuario.put("email", doacao.getUsuario().getEmail());
            jsonUsuario.put("telefone", doacao.getUsuario().getTelefone());

            Map<String, Object> jsonDoacao = new HashMap<>();
            jsonDoacao.put("codDoacao", doacao.getCodDoacao());
            jsonDoacao.put("status", doacao.getStatus());
            jsonDoacao.put("data", doacao.getData());
            jsonDoacao.put("valor", doacao.getValor());
            jsonDoacao.put("usuario", jsonUsuario);

            return jsonDoacao;
        }

        // Retorna null se a adocao não for encontrado
        return null;
    }

    public boolean onAlterar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Doacao doacaoNovo = new Doacao();
            Doacao doacao = new Doacao();

            Doacao doacaoAntigo;
            Usuario usuario;

            // Recupera os dados antigos e novos
            doacaoAntigo = doacao.consultarID((int) json.get("codDoacao"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);

            // Preenche o novo objeto de adoção
            doacaoNovo.setCodDoacao((int) json.get("codDoacao"));
            doacaoNovo.setUsuario(usuario);
            doacaoNovo.setStatus(json.get("status").toString());
            doacaoNovo.setData(json.get("data").toString());
            doacaoNovo.setValor((int) json.get("valor"));
            return doacaoNovo.alterar(conexao);
        }

        return false;
    }

    public boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("status") && json.containsKey("data") && json.containsKey("valor") && json.containsKey("usuario")) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            // Verificar se os objetos existem no banco
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario usuario;
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            if (usuario != null)
                return true;
        }
        return false;
    }

    public boolean validarAlterar(Map<String, Object> json) {
        if (json != null && json.containsKey("status") && json.containsKey("data") && json.containsKey("valor") && json.containsKey("usuario") && json.containsKey("codDoacao")) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();
            Doacao doacao = new Doacao().consultarID((int) json.get("codDoacao"), conexao);

            if (doacao != null) {
                UsuarioDAO usuarioDAO = new UsuarioDAO();

                Usuario usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
                if (usuario != null)
                    return true;
            }
        }

        return false;
    }

    public List<Map<String, Object>> onBuscarPorUsuario(int codUsuario) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Doacao doacao = new Doacao();
        List<Doacao> lista = doacao.consultarPorUsuario(codUsuario, conexao);

        if (lista != null) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (Doacao a : lista) {
                Map<String, Object> jsonUsuario = new HashMap<>();
                jsonUsuario.put("cod", a.getUsuario().getCod());
                jsonUsuario.put("nome", a.getUsuario().getNome());
                jsonUsuario.put("email", a.getUsuario().getEmail());
                jsonUsuario.put("telefone", a.getUsuario().getTelefone());

                Map<String, Object> jsonDoacao = new HashMap<>();
                jsonDoacao.put("codDoacao", a.getCodDoacao());
                jsonDoacao.put("status", a.getStatus());
                jsonDoacao.put("data", a.getData());
                jsonDoacao.put("valor", a.getValor());
                jsonDoacao.put("usuario", jsonUsuario);

                listaJson.add(jsonDoacao);
            }
            return listaJson;
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> buscarPorData(String dataInicio, String dataFim) {
        List<Map<String, Object>> lista = new ArrayList<>();

        String sql = """
        SELECT doa_id AS codDoacao, doa_data AS data, doa_valor AS valor, doa_status AS status
        FROM doacao
        WHERE CAST(doa_data AS DATE) BETWEEN ? AND ?
        AND doa_status = 'Aprovada'
        ORDER BY doa_data
    """;

        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        try {
            PreparedStatement stmt = conexao.getPreparedStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(dataInicio));
            stmt.setDate(2, java.sql.Date.valueOf(dataFim));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("codDoacao", rs.getInt("codDoacao"));
                registro.put("data", rs.getDate("data").toString());
                registro.put("valor", rs.getDouble("valor"));
                registro.put("status", rs.getString("status"));
                lista.add(registro);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }



}