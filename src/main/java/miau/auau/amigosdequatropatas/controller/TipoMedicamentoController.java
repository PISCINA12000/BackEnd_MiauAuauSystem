package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.TipoMedicamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TipoMedicamentoController {

    @Autowired
    private TipoMedicamento tipoMedicamentoModel;

    public boolean onGravar(Map<String, Object> json) {
        if(validar(json)) {
            //criando a conexao
            SingletonDB singletonDB = new SingletonDB();
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
        SingletonDB singletonDB = new SingletonDB();
        Conexao conexao = singletonDB.getConexao();

        tipoMedicamentoModel = tipoMedicamentoModel.consultarID(id, conexao);
        if(tipoMedicamentoModel != null) {
            return tipoMedicamentoModel.excluir(conexao);
        }
        return false;
    }

    public TipoMedicamento onBuscarId(int id) {
        if (tipoMedicamentoModel.consultarID(id) != null) // achou tipo de medicamento
        {
            return tipoMedicamentoModel.consultarID(id);
        } else
            return null;
    }

    public List<TipoMedicamento> onBuscar(String filtro) {
        if (tipoMedicamentoModel.consultar(filtro) != null) {
            return tipoMedicamentoModel.consultar(filtro);
        } else
            return null;
    }

    public boolean onAlterar(TipoMedicamento tipoMedicamento) {
        if (validarAlterar(tipoMedicamento)) {
            return tipoMedicamentoModel.alterar(tipoMedicamento);
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
