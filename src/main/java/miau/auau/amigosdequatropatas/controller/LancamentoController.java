package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Lancamento;
import miau.auau.amigosdequatropatas.entities.TipoLancamento;
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
        if(validar(json)){
            lancamento.setCodTpLanc(Integer.parseInt(json.get("codTpLanc").toString()));
            if(validarAnimal(json)) //se o animal existir então eu seto no meu objeto
                lancamento.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));
            else //se não existir ele vai 0 como default
                lancamento.setCodAnimal(0);
            lancamento.setData(json.get("data").toString());
            lancamento.setDebito(Integer.parseInt(json.get("debito").toString()));
            lancamento.setCredito(Integer.parseInt(json.get("credito").toString()));
            lancamento.setDescricao(json.get("descricao").toString());
            lancamento.setValor(Double.parseDouble(json.get("valor").toString()));
            lancamento.setPDF((byte[]) json.get("pdf"));
        }
        else
            return false;

        //se chegou até aqui está tudo certo
        if(lancamento.incluir(conexao))
            return true;
        return false;
    }

    public boolean onDelete(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Lancamento lanc = lancamento.consultarID(id, conexao);
        // Se o animal for encontrado, exclui; caso contrário, retorna false
        if (lanc != null)
            return lancamento.excluir(conexao);
        return false;
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
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar modelo de TipoLancamento e Animal
        TipoLancamento tipoLancamento = new TipoLancamento();
        Animal animal = new Animal();

        //nesse return eu realizo 4 consultas para saber se de fato todos os códigos são válidos
        return
                tipoLancamento.consultarID(Integer.parseInt(json.get("codTpLanc").toString()),conexao) != null &&
                !json.get("data").toString().isEmpty() &&
                tipoLancamento.consultarID(Integer.parseInt(json.get("debito").toString()),conexao) != null &&
                tipoLancamento.consultarID(Integer.parseInt(json.get("credito").toString()),conexao) != null &&
                !json.get("descricao").toString().isEmpty() &&
                Double.parseDouble(json.get("valor").toString()) > 0;
    }

    public boolean validarAtualizar(Map<String, Object> json) {
        return Integer.parseInt(json.get("id").toString()) > 0 && validar(json);
    }

    public boolean validarAnimal(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar um animal para consultar se o mesmo recebido existe
        Animal animal = new Animal();

        return animal.consultarID(Integer.parseInt(json.get("codAnimal").toString()),conexao) != null;
    }
}
