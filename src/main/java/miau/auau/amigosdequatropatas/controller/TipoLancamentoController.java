package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.TipoLancamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TipoLancamentoController {

    @Autowired
    private TipoLancamento tipoLancamentoModel;

    public boolean onGravar(Map<String, Object> json) {
        if (validar(json)) {
            //gerar a conexao
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();
            tipoLancamentoModel.setDescricao((String) json.get("descricao"));
            if(tipoLancamentoModel.incluir(conexao)) {
                return true;
            }
            return false;
        }
        else
            return false;
    }

    public boolean onDelete(int id) {
        //criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        TipoLancamento tipoLancamento = tipoLancamentoModel.consultarID(id, conexao);
        if (tipoLancamento != null) {
            return tipoLancamento.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciando um JSON
        Map<String, Object> json = new HashMap<>();

        TipoLancamento tipoLancamento = tipoLancamentoModel.consultarID(id, conexao);
        if(tipoLancamento != null) {
            json.put("cod", tipoLancamento.getCod());
            json.put("descricao", tipoLancamento.getDescricao());
            return json;
        }

        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        List<TipoLancamento> lista;

        //realizando a busca
        lista = tipoLancamentoModel.consultar(filtro, conexao);

        //verifica se algo retornou para a minha lista
        if(!lista.isEmpty()) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for(int i=0; i<lista.size(); i++) {
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
        //realizando a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        if (validarAlterar(json)) {
            tipoLancamentoModel.setCod(Integer.parseInt(json.get("cod").toString()));
            tipoLancamentoModel.setDescricao((String) json.get("descricao"));
            return tipoLancamentoModel.alterar(conexao);
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
