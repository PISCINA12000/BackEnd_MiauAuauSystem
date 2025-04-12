package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.TipoMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TipoMedicamentoController {

    @Autowired
    private TipoMedicamento tipoMedicamentoModel;

    public boolean onGravar(Map<String, Object> json) {
        if(validar(json)) {
            //criando a conexao
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            tipoMedicamentoModel.setNome((String) json.get("nome"));
            if (tipoMedicamentoModel.incluir(conexao))
                return true;

            //chegando aqui algo deu errado
            return false;
        }
        return false;
    }

    public boolean onDelete(int id) {
        //criando a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        tipoMedicamentoModel = tipoMedicamentoModel.consultarID(id, conexao);
        if(tipoMedicamentoModel != null) {
            return tipoMedicamentoModel.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        //criando a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //buscar pelo id
        tipoMedicamentoModel = tipoMedicamentoModel.consultarID(id, conexao);
        if(tipoMedicamentoModel != null) {
            //achou um medicamento
            Map<String, Object> json = new HashMap<>();
            json.put("cod", tipoMedicamentoModel.getCod());
            json.put("nome", tipoMedicamentoModel.getNome());
            return json;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //Criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        List<TipoMedicamento> lista = tipoMedicamentoModel.consultar(filtro, conexao);

        if(!lista.isEmpty()) {
            //achou tipos de medicamentos
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", lista.get(i).getCod());
                json.put("nome", lista.get(i).getNome());
                listaJson.add(json);
            }
            return listaJson;
        }
        return null;
    }

    public boolean onAlterar(Map<String, Object> json) {
        //Criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //colocar valores na instância da entidade
        tipoMedicamentoModel.setCod(Integer.parseInt((String) json.get("cod")));
        tipoMedicamentoModel.setNome((String) json.get("nome"));

        if (validarAlterar(json)) {
            return tipoMedicamentoModel.alterar(conexao);
        } else
            return false;
    }

    public boolean validar(Map<String, Object> json) {
        return !json.isEmpty() && !json.get("nome").toString().isEmpty();
    }

    public boolean validarAlterar(Map<String, Object> json) {
        return validar(json) && Integer.parseInt(json.get("cod").toString()) > 0;
    }
}
