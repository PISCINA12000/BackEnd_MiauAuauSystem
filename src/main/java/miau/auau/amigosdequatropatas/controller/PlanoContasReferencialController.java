package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.PlanoContasReferencial;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlanoContasReferencialController {
    @Autowired
    private PlanoContasReferencial planoContasReferencialModel;

    public boolean onGravar(Map<String, Object> json) {
        if (validar(json)) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            planoContasReferencialModel.setDescricao(json.get("descricao").toString());
            planoContasReferencialModel.setNatureza(json.get("natureza").toString());
            planoContasReferencialModel.setClassificacao(json.get("classificacao").toString());
            if (planoContasReferencialModel.incluir(conexao))
                return true;
            return false;
        }
        return false;
    }

    public boolean onDelete(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        PlanoContasReferencial planoContasReferencial = planoContasReferencialModel.consultarID(id, conexao);
        if (planoContasReferencial != null) {
            return planoContasReferencial.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Map<String, Object> json = new HashMap<>();
        PlanoContasReferencial planoContasReferencial = planoContasReferencialModel.consultarID(id, conexao);
        if (planoContasReferencial != null) {
            json.put("cod", planoContasReferencial.getCod());
            json.put("descricao", planoContasReferencial.getDescricao());
            json.put("natureza", planoContasReferencial.getNatureza());
            json.put("classificacao", planoContasReferencial.getClassificacao());
            return json;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<PlanoContasReferencial> lista = planoContasReferencialModel.consultar(filtro, conexao);

        if (lista != null && !lista.isEmpty()) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", lista.get(i).getCod());
                json.put("descricao", lista.get(i).getDescricao());
                json.put("natureza", lista.get(i).getNatureza());
                json.put("classificacao", lista.get(i).getClassificacao());
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
            planoContasReferencialModel.setCod(Integer.parseInt(json.get("cod").toString()));
            planoContasReferencialModel.setDescricao(json.get("descricao").toString());
            planoContasReferencialModel.setNatureza(json.get("natureza").toString());
            planoContasReferencialModel.setClassificacao(json.get("classificacao").toString());
            return planoContasReferencialModel.alterar(conexao);
        }
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        return !json.isEmpty() &&
                !json.get("descricao").toString().isEmpty() &&
                !json.get("natureza").toString().isEmpty() &&
                !json.get("classificacao").toString().isEmpty();
    }

    public boolean validarAlterar(Map<String, Object> json) {
        return validar(json) && Integer.parseInt(json.get("cod").toString()) > 0;
    }
}
