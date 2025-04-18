package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Lancamento;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LancamentoController {
    @Autowired
    private Lancamento lancamento;

    public boolean onGravar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if(/*valido(json)*/true){
            lancamento.setCodTpLanc(Integer.parseInt(json.get("codTpLanc").toString()));
            lancamento.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));
            lancamento.setData(json.get("data").toString());
            lancamento.setDebito(Integer.parseInt(json.get("debito").toString()));
            lancamento.setCredito(Integer.parseInt(json.get("credito").toString()));
            lancamento.setDescricao(json.get("descricao").toString());
            lancamento.setValor(Double.parseDouble(json.get("valor").toString()));
            lancamento.setPDF((byte[]) json.get("pdf"));
        }
        if(/*existe crédito especificado*/true)
            lancamento.setCredito(Integer.parseInt(json.get("credito").toString()));
        if(/*existe débito especificado*/true)
            lancamento.setDebito(Integer.parseInt(json.get("debito").toString()));
        if(/*existe o cod do animal*/true)
            lancamento.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));
        else
            lancamento.setCodAnimal(0);
        if(/*a data especificada não é maior que o dia atual*/true)
            lancamento.setDescricao(json.get("descricao").toString());

        return true; //temp
    }

    public boolean onDelete(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        return true; //temp
    }

    public boolean onAtualizar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        return true; //temp
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        return null; //temp
    }

    public Map<String, Object> onBuscarID(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        return null;
    }

    public boolean validar(Map<String, Object> json) {
        return true; //temp
    }
}
