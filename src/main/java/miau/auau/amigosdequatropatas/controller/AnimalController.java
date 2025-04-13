package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AnimalController {

    @Autowired
    private Animal animalModel;

    public boolean onGravar(Map<String, Object> json) {


        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            //transferir oq chegou de JSON para a instência de Animal
            animalModel.setNome(json.get("nome").toString());
            animalModel.setSexo(json.get("sexo").toString());
            animalModel.setRaca(json.get("raca").toString());
            animalModel.setIdade(Integer.parseInt(json.get("idade").toString()));
            animalModel.setPeso(Double.parseDouble(json.get("peso").toString()));
            animalModel.setCastrado(json.get("castrado").toString());
            animalModel.setAdotado(json.get("adotado").toString());
            animalModel.setImagemBase64(json.get("imagemBase64").toString());

            if (animalModel.incluir(conexao)) {
                // commit; finalizar transação e desconectar
                return true;
            }

            //se chegou até aqui é porque algo deu errado
            // rollback; finalizar t. desconecta;
            return false;
        }
        return false;
    }

    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando o animal pelo ID
        Animal animal = animalModel.consultarID(id, conexao);
        // Se o animal for encontrado, exclui; caso contrário, retorna false
        if (animal != null) {

            return animal.excluir(conexao);

        }

        return false;
    }


    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando o animal pelo ID
        Animal animal = animalModel.consultarID(id, conexao);

        // Retornando os dados do animal, se encontrado
        if (animal != null) {
            Map<String, Object> json = new HashMap<>();
            json.put("codAnimal", animal.getCodAnimal());
            json.put("nome", animal.getNome());
            json.put("sexo", animal.getSexo());
            json.put("raca", animal.getRaca());
            json.put("idade", animal.getIdade());
            json.put("peso", animal.getPeso());
            json.put("castrado", animal.getCastrado());
            json.put("adotado", animal.getAdotado());
            json.put("imagemBase64", animal.getImagemBase64());
            return json;
        }

        // Retorna null se o animal não for encontrado
        return null;
    }


    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //criando a lista que conterá os JSON's
        List<Animal> lista = animalModel.consultar(filtro, conexao);

        //verificação de a minha lista JSON está vazia ou não
        if (!lista.isEmpty()) {
            //crio uma lista json contendo os animais que retornaram no meu consultar
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i=0; i<lista.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("codAnimal", lista.get(i).getCodAnimal());
                json.put("nome", lista.get(i).getNome());
                json.put("sexo", lista.get(i).getSexo());
                json.put("raca", lista.get(i).getRaca());
                json.put("idade", lista.get(i).getIdade());
                json.put("peso", lista.get(i).getPeso());
                json.put("castrado", lista.get(i).getCastrado());
                json.put("adotado", lista.get(i).getAdotado());
                json.put("imagemBase64", lista.get(i).getImagemBase64());
                listaJson.add(json);
            }
            return listaJson;
        }
        else
            return null;
    }

    public boolean onAlterar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //Transferir o JSON recebido para a instância de modelo
        animalModel.setCodAnimal((Integer)json.get("codAnimal"));
        animalModel.setNome((String)json.get("nome"));
        animalModel.setSexo((String)json.get("sexo"));
        animalModel.setRaca((String)json.get("raca"));
        animalModel.setIdade((Integer)json.get("idade"));
        animalModel.setPeso((Double)json.get("peso"));
        animalModel.setCastrado((String)json.get("castrado"));
        animalModel.setAdotado((String)json.get("adotado"));
        animalModel.setImagemBase64((String)json.get("imagemBase64"));

        //velidar se deu certo ou não o alterar
        if (validarAlterar(json))
            return animalModel.alterar(conexao);
        else
            return false;
    }

    public boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        return json != null &&
                json.containsKey("nome") &&
                json.containsKey("sexo") &&
                json.containsKey("raca") &&
                json.containsKey("idade") &&
                json.containsKey("peso") &&
                json.containsKey("castrado") &&
                json.containsKey("adotado") &&
                json.containsKey("imagemBase64");
    }

    public boolean validarAlterar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        return validar(json) && json.containsKey("codAnimal");
    }
}
