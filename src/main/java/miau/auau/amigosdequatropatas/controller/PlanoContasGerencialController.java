package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.PlanoContasGerencial;
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
public class PlanoContasGerencialController {
    @Autowired
    private PlanoContasGerencial planoContasGerencialModel;

    public boolean onGravar(Map<String, Object> json) {
        if (validar(json)) {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            planoContasGerencialModel.setDescricao(json.get("descricao").toString());
            planoContasGerencialModel.setCodPcr(Integer.parseInt(json.get("codPcr").toString()));
            if (planoContasGerencialModel.incluir(conexao))
                return true;
            return false;
        } else
            return false;
    }

    public boolean onDelete(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        PlanoContasGerencial planoContasGerencial = planoContasGerencialModel.consultarID(id, conexao);
        if (planoContasGerencial != null) {
            return planoContasGerencial.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Map<String, Object> json = new HashMap<>();
        PlanoContasGerencial planoContasGerencial = planoContasGerencialModel.consultarID(id, conexao);
        if (planoContasGerencial != null) {
            PlanoContasReferencial planoReferencial = new PlanoContasReferencial();
            planoReferencial = planoReferencial.consultarID(planoContasGerencial.getCodPcr(), conexao);

            json.put("cod", planoContasGerencial.getCod());
            json.put("descricao", planoContasGerencial.getDescricao());
            json.put("referencial", planoReferencial);
            return json;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<PlanoContasGerencial> lista = planoContasGerencialModel.consultar(filtro, conexao);

        if (lista != null && !lista.isEmpty()) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                PlanoContasReferencial planoReferencial = new PlanoContasReferencial();
                planoReferencial = planoReferencial.consultarID(lista.get(i).getCodPcr(), conexao);
                Map<String, Object> json = new HashMap<>();
                json.put("cod", lista.get(i).getCod());
                json.put("descricao", lista.get(i).getDescricao());
                json.put("referencial", planoReferencial);
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
            planoContasGerencialModel.setCod(Integer.parseInt(json.get("cod").toString()));
            planoContasGerencialModel.setDescricao(json.get("descricao").toString());
            planoContasGerencialModel.setCodPcr(Integer.parseInt(json.get("codPcr").toString()));
            return planoContasGerencialModel.alterar(conexao);
        }
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        return !json.isEmpty() &&
                !json.get("descricao").toString().isEmpty() &&
                Integer.parseInt(json.get("codPcr").toString()) > 0;
    }

    public boolean validarAlterar(Map<String, Object> json) {
        return validar(json) && Integer.parseInt(json.get("cod").toString()) > 0;
    }
}
