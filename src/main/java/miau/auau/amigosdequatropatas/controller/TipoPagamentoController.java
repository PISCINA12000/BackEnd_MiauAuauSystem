package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.TipoPagamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TipoPagamentoController {
    @Autowired
    private TipoPagamento tipoPagamentoModel;

    public boolean onGravar(Map<String, Object> json) {
        if (validar(json)) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            tipoPagamentoModel.setDescricao((String) json.get("descricao"));
            if (tipoPagamentoModel.incluir(conexao))
                return true;
            return false;
        } else
            return false;
    }

    public boolean onDelete(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        TipoPagamento tipoPagamento = tipoPagamentoModel.consultarID(id, conexao);
        if (tipoPagamento != null) {
            return tipoPagamento.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Map<String, Object> json = new HashMap<>();
        TipoPagamento tipoPagamento = tipoPagamentoModel.consultarID(id, conexao);
        if (tipoPagamento != null) {
            json.put("cod", tipoPagamento.getCod());
            json.put("descricao", tipoPagamento.getDescricao());
            return json;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<TipoPagamento> lista = tipoPagamentoModel.consultar(filtro, conexao);

        if (lista != null && !lista.isEmpty()) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", lista.get(i).getCod());
                json.put("descricao", lista.get(i).getDescricao());
                listaJson.add(json);
            }
            return listaJson;
        }
        return null;
    }

    public boolean onAlterar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json)) {
            tipoPagamentoModel.setCod(Integer.parseInt(json.get("cod").toString()));
            tipoPagamentoModel.setDescricao((String) json.get("descricao"));
            return tipoPagamentoModel.alterar(conexao);
        }
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        return !json.isEmpty() && !json.get("descricao").toString().isEmpty();
    }

    public boolean validarAlterar(Map<String, Object> json) {
        return validar(json) && Integer.parseInt(json.get("cod").toString()) > 0;
    }
}
