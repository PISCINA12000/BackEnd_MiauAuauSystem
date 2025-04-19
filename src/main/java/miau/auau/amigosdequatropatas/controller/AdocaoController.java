package miau.auau.amigosdequatropatas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.entities.Adocao;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdocaoController {

    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            Adocao adocao = new Adocao();
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Animal animal;
            Usuario usuario;

            animal = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            String data = json.get("data").toString();
            String status = json.get("status").toString();

            adocao.setData(data);
            adocao.setStatus(status);
            adocao.setAnimal(animal);
            adocao.setUsuario(usuario);

            if (adocao.incluir(conexao)) {
                // commit; finalizar transação e desconectar
                return true;
            }

            //se chegou até aqui é porque algo deu errado
            // rollback; finalizar t. desconecta;
            return false;
        }
        return false;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //criando a lista que conterá os JSON's
        Adocao adocao = new Adocao();
        List<Adocao> lista = adocao.consultar(filtro, conexao);

        //verificação de a minha lista JSON está vazia ou não
        if (lista!=null) {
            //crio uma lista json contendo os animais que retornaram no meu consultar
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i=0; i<lista.size(); i++) {

                Adocao a = lista.get(i);
                // dados animal
                Map<String, Object> jsonAnimal = new HashMap<>();
                jsonAnimal.put("codAnimal", a.getAnimal().getCodAnimal());
                jsonAnimal.put("nome", a.getAnimal().getNome());
                jsonAnimal.put("sexo", a.getAnimal().getSexo());
                jsonAnimal.put("raca", a.getAnimal().getRaca());
                jsonAnimal.put("idade", a.getAnimal().getIdade());
                jsonAnimal.put("peso", a.getAnimal().getPeso());
                jsonAnimal.put("castrado", a.getAnimal().getCastrado());
                jsonAnimal.put("adotado", a.getAnimal().getAdotado());
                jsonAnimal.put("imagemBase64", a.getAnimal().getImagemBase64());

                Map<String, Object> jsonUsuario = new HashMap<>();
                jsonUsuario.put("codUsuario", a.getUsuario().getCod());
                jsonUsuario.put("nome", a.getUsuario().getNome());
                jsonUsuario.put("email", a.getUsuario().getEmail());
                jsonUsuario.put("telefone", a.getUsuario().getTelefone());
                jsonUsuario.put("cpf", a.getUsuario().getCpf());
                jsonUsuario.put("sexo", a.getUsuario().getSexo());
                jsonUsuario.put("privilegio", a.getUsuario().getPrivilegio());
                jsonUsuario.put("cep", a.getUsuario().getCep());
                jsonUsuario.put("bairro", a.getUsuario().getBairro());
                jsonUsuario.put("numero", a.getUsuario().getNumero());
                jsonUsuario.put("rua", a.getUsuario().getRua());

                Map<String, Object> jsonAdocao = new HashMap<>();
                jsonAdocao.put("codAdocao", a.getCodAdocao());
                jsonAdocao.put("data", a.getData());
                jsonAdocao.put("status", a.getStatus());
                jsonAdocao.put("animal", jsonAnimal);
                jsonAdocao.put("usuario", jsonUsuario);
                listaJson.add(jsonAdocao);
            }
            return listaJson;
        }
        else
            return null;
    }

    public boolean onDelete(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Adocao adocao = new Adocao();
        adocao = adocao.consultarID(id, conexao);
        // Se a adocao for encontrada, exclui; caso contrário, retorna false
        if (adocao != null)
            return adocao.excluir(conexao);
        return false;
    }


    public Map<String, Object> onBuscarId(int id) {
        // Criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        // Consultando a adocao pelo ID
        Adocao adocao = new Adocao();
        adocao = adocao.consultarID(id, conexao);

        // Retornando os dados da adocao, se encontrado
        if (adocao != null) {

            Map<String, Object> jsonAnimal = new HashMap<>();
            jsonAnimal.put("codAnimal", adocao.getAnimal().getCodAnimal());
            jsonAnimal.put("nome", adocao.getAnimal().getNome());
            jsonAnimal.put("sexo", adocao.getAnimal().getSexo());
            jsonAnimal.put("raca", adocao.getAnimal().getRaca());
            jsonAnimal.put("idade", adocao.getAnimal().getIdade());
            jsonAnimal.put("peso", adocao.getAnimal().getPeso());
            jsonAnimal.put("castrado", adocao.getAnimal().getCastrado());
            jsonAnimal.put("adotado", adocao.getAnimal().getAdotado());
            jsonAnimal.put("imagemBase64", adocao.getAnimal().getImagemBase64());

            Map<String, Object> jsonUsuario = new HashMap<>();
            jsonUsuario.put("codUsuario", adocao.getUsuario().getCod());
            jsonUsuario.put("nome", adocao.getUsuario().getNome());
            jsonUsuario.put("email", adocao.getUsuario().getEmail());
            jsonUsuario.put("telefone", adocao.getUsuario().getTelefone());
            jsonUsuario.put("cpf", adocao.getUsuario().getCpf());
            jsonUsuario.put("sexo", adocao.getUsuario().getSexo());
            jsonUsuario.put("privilegio", adocao.getUsuario().getPrivilegio());
            jsonUsuario.put("cep", adocao.getUsuario().getCep());
            jsonUsuario.put("bairro", adocao.getUsuario().getBairro());
            jsonUsuario.put("numero", adocao.getUsuario().getNumero());
            jsonUsuario.put("rua", adocao.getUsuario().getRua());


            Map<String, Object> jsonAdocao = new HashMap<>();
            jsonAdocao.put("codAdocao", adocao.getCodAdocao());
            jsonAdocao.put("data", adocao.getData());
            jsonAdocao.put("status", adocao.getStatus());
            jsonAdocao.put("animal", jsonAnimal);
            jsonAdocao.put("usuario", jsonUsuario);

            return jsonAdocao;
        }

        // Retorna null se a adocao não for encontrado
        return null;
    }
    public boolean onAlterar(Map<String, Object> json) {
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        if (validarAlterar(json))
        {
            Adocao adocao = new Adocao();
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Animal animal;
            Usuario usuario;
            animal = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            adocao.setCodAdocao((int)json.get("codAdocao"));
            adocao.setAnimal(animal);
            adocao.setUsuario(usuario);
            adocao.setData(json.get("data").toString());
            adocao.setStatus(json.get("status").toString());

            return adocao.alterar(conexao);
        }
        else
            return false;
    }

    public boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("data") && json.containsKey("usuario") && json.containsKey("animal"))
        {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            // Verificar se os objetos existem no banco
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Animal animal;
            Usuario usuario;
            animal = animalDAO.get((int) json.get("animal"), conexao);
            usuario = usuarioDAO.get((int) json.get("usuario"), conexao);
            if (usuario != null && animal != null && animal.getAdotado().equals("Não"))
                return true;
        }

        return false;
    }

    public boolean validarAlterar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        return validar(json) && json.containsKey("codAdocao");
    }


}
