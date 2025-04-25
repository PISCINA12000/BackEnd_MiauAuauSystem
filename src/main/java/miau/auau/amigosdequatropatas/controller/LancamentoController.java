package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Lancamento;
import miau.auau.amigosdequatropatas.entities.TipoLancamento;
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
public class LancamentoController {
    @Autowired
    private Lancamento lancamento;

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Lancamento> lancamentos = lancamento.consultar(filtro, conexao);
        if(lancamentos!=null && !lancamentos.isEmpty()) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for(int i=0; i<lancamentos.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", lancamentos.get(i).getCod());
                json.put("data", lancamentos.get(i).getData());

                //tratar do tipoLançamento
                TipoLancamento tipoLancamento = new TipoLancamento();
                tipoLancamento = tipoLancamento.consultarID(lancamentos.get(i).getCodTpLanc(), conexao);
                json.put("TpLanc", tipoLancamento);

                //tratar do animal
                if(lancamentos.get(i).getCodAnimal()==0)
                    json.put("animal", null);
                else {
                    Animal animal = new Animal();
                    animal = animal.consultarID(lancamentos.get(i).getCodAnimal(), conexao);
                    json.put("animal", animal);
                }

                //tratar débito e crédito
                TipoPagamento tipoPagamento = new TipoPagamento();
                tipoPagamento = tipoPagamento.consultarID(lancamentos.get(i).getDebito(), conexao);
                json.put("debito", tipoPagamento);
                tipoPagamento = tipoPagamento.consultarID(lancamentos.get(i).getCredito(), conexao);
                json.put("credito", tipoPagamento);

                json.put("descricao", lancamentos.get(i).getDescricao());
                json.put("valor", lancamentos.get(i).getValor());
                json.put("arquivo", lancamentos.get(i).getPDF());
                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    public Map<String, Object> onBuscarID(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Lancamento lanc = lancamento.consultarID(id, conexao);
        if(lanc!=null) {
            Map<String, Object> json = new HashMap<>();
            json.put("cod", lanc.getCod());
            json.put("data", lanc.getData());

            //tratar do tipoLançamento
            TipoLancamento tipoLancamento = new TipoLancamento();
            tipoLancamento = tipoLancamento.consultarID(lanc.getCodTpLanc(), conexao);
            json.put("TpLanc", tipoLancamento);

            //tratar o animal
            if(lanc.getCodAnimal()==0)
                json.put("animal", null);
            else {
                Animal animal = new Animal();
                animal = animal.consultarID(lanc.getCodAnimal(), conexao);
                json.put("animal", animal);
            }

            //tratar débito e crédito
            TipoPagamento tipoPagamento = new TipoPagamento();
            tipoPagamento = tipoPagamento.consultarID(lanc.getDebito(), conexao);
            json.put("debito", tipoPagamento);
            tipoPagamento = tipoPagamento.consultarID(lanc.getCredito(), conexao);
            json.put("credito", tipoPagamento);

            json.put("descricao", lanc.getDescricao());
            json.put("valor", lanc.getValor());
            json.put("arquivo", lanc.getPDF());
            return json;
        }
        return null;
    }

    public byte[] onBuscarPDF(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Lancamento lanc = lancamento.consultarID(id, conexao);
        if (lanc != null) {
            return lanc.getPDF();
        }
        return null;
    }

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
            lancamento.setPDF((byte[]) json.get("arquivo"));
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
        if (lanc != null)
            return lanc.excluir(conexao);
        return false;
    }

    public boolean onAtualizar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if(validarAtualizar(json)){
            lancamento.setCod(Integer.parseInt(json.get("id").toString()));
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
            lancamento.setPDF((byte[]) json.get("arquivo"));
        }
        else
            return false;

        //se chegou até aqui está tudo certo
        if(lancamento.alterar(conexao))
            return true;
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar modelo de TipoLancamento e Animal
        TipoLancamento tipoLancamento = new TipoLancamento();
        TipoPagamento tipoPagamento = new TipoPagamento();

        //nesse return eu realizo 4 consultas para saber se de fato todos os códigos são válidos
        return
                tipoLancamento.consultarID(Integer.parseInt(json.get("codTpLanc").toString()),conexao) != null &&
                !json.get("data").toString().isEmpty() &&
                tipoPagamento.consultarID(Integer.parseInt(json.get("debito").toString()),conexao) != null &&
                tipoPagamento.consultarID(Integer.parseInt(json.get("credito").toString()),conexao) != null &&
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
