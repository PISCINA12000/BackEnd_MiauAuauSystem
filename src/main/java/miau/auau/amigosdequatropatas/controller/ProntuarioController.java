package miau.auau.amigosdequatropatas.controller;


import miau.auau.amigosdequatropatas.entities.*;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProntuarioController {

    @Autowired
    private Prontuario prontuario;

    //filtrar registro pelo tipo do registro ou nao e ordena pela data com filtro ou n
    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios = prontuario.consultar(filtro, conexao);
        if (prontuarios != null && !prontuarios.isEmpty()) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    //retorna registros de um periodo
    public List<Map<String, Object>> onBuscarData(String dataIni, String dataFim) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios = prontuario.consultarPorData(dataIni, dataFim, conexao);
        if (prontuarios != null && !prontuarios.isEmpty()) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;
    }

    //retorna todos os registros de um animal
    public List<Map<String, Object>> onBuscarPorIdAnimal(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        List<Prontuario> prontuarios= prontuario.consultarPorIdAnimal(id, conexao);

        if (prontuarios != null /*&& !prontuarios.isEmpty()*/) {
            List<Map<String, Object>> lista = new ArrayList<>();
            for (int i = 0; i < prontuarios.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", prontuarios.get(i).getCod());


                Animal animal = new Animal();
                animal = animal.consultarID(prontuarios.get(i).getCodAnimal(), conexao);
                json.put("animal", animal);


                json.put("data", prontuarios.get(i).getData());


                json.put("tipoRegistro", prontuarios.get(i).getTipoRegistro());
                json.put("observacao", prontuarios.get(i).getObservacao());
                json.put("arquivo", prontuarios.get(i).getPDF());

                lista.add(json);
            }
            return lista;
        }
        return null;

    }

    public Map<String, Object> onBuscarId(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null) {
            Map<String, Object> json = new HashMap<>();
            json.put("cod", pron.getCod());

            //tratar animal
            Animal animal = new Animal();
            animal = animal.consultarID(pron.getCodAnimal(), conexao);
            json.put("animal", animal);

            json.put("data", pron.getData());

            json.put("tipoRegistro", pron.getTipoRegistro());
            json.put("observacao", pron.getObservacao());
            json.put("arquivo", pron.getPDF());

            return json;
        }
        return null;
    }


    public byte[] onBuscarPDF(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null) {
            return pron.getPDF();
        }
        return null;
    }


    public boolean onGravar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if (validar(json)) {
            //prontuario.setCod(Integer.parseInt(json.get("cod").toString()));

            prontuario.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));

            prontuario.setData(json.get("data").toString());
            prontuario.setTipoRegistro(json.get("tipoRegistro").toString());
            prontuario.setObservacao(json.get("observacao").toString());

            prontuario.setPDF((byte[]) json.get("arquivo"));
        } else
            return false;

        //se chegou até aqui está tudo certo
        if (prontuario.incluir(conexao))
            return true;
        return false;
    }

    public boolean onDelete(int id) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Prontuario pron = prontuario.consultarID(id, conexao);
        if (pron != null)
            return pron.excluir(conexao);
        return false;
    }

    public boolean onAtualizar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //onde vou setar minhas informações seguindo as regras de negócios
        if (validarAtualizar(json)) {
            prontuario.setCod(Integer.parseInt(json.get("cod").toString()));

            prontuario.setCodAnimal(Integer.parseInt(json.get("codAnimal").toString()));

            prontuario.setData(json.get("data").toString());
            prontuario.setTipoRegistro(json.get("tipoRegistro").toString());
            prontuario.setObservacao(json.get("observacao").toString());

            prontuario.setPDF((byte[]) json.get("arquivo"));
        } else
            return false;

        //se chegou até aqui está tudo certo
        if (prontuario.alterar(conexao))
            return true;
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //instanciar modelo Animal
        Animal animal = new Animal();


        //nesse return eu realizo 4 consultas para saber se de fato todos os códigos são válidos
        return animal.consultarID(Integer.parseInt(json.get("codAnimal").toString()), conexao) != null &&
                        !json.get("data").toString().isEmpty() &&
                        !json.get("tipoRegistro").toString().isEmpty() &&
                        !json.get("observacao").toString().isEmpty();
    }

    public boolean validarAtualizar(Map<String, Object> json) {
        return Integer.parseInt(json.get("cod").toString()) > 0 && validar(json);
    }


}
