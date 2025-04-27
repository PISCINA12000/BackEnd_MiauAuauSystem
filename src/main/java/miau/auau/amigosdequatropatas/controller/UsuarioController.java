package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsuarioController {

    @Autowired
    private Usuario usuarioModel;

    public boolean onGravar(Map<String, Object> json) {
        if (validar(json)) {
            //criar a conexao
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            //setar valores para o usuario model
            usuarioModel.setNome(json.get("nome").toString());
            usuarioModel.setEmail(json.get("email").toString());
            usuarioModel.setSenha(json.get("senha").toString());
            usuarioModel.setTelefone(json.get("telefone").toString());
            usuarioModel.setCpf(json.get("cpf").toString());
            usuarioModel.setPrivilegio(json.get("privilegio").toString());
            usuarioModel.setSexo(json.get("sexo").toString());
            usuarioModel.setCep(json.get("cep").toString());
            usuarioModel.setRua(json.get("rua").toString());
            usuarioModel.setBairro(json.get("bairro").toString());
            usuarioModel.setNumero(json.get("numero").toString());
            usuarioModel.setCidade(json.get("cidade").toString());
            usuarioModel.setEstado(json.get("estado").toString());
            return usuarioModel.incluir(conexao);
        }
        return false;
    }

    public boolean onDelete(int id) {
        //criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        Usuario usuario =  usuarioModel.consultarID(id,conexao); //consulto para confirmar se existe ou não
        if (usuario != null) {
            return usuario.excluir(conexao);
        }
        return false;
    }

    public Map<String, Object> onBuscarId(int id) {
        //criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        //mandar para a modelo
        Usuario usuario = usuarioModel.consultarID(id, conexao);
        if (usuario != null) {
            Map<String, Object> json = new HashMap<>();
            json.put("cod", usuario.getCod());
            json.put("nome", usuario.getNome());
            json.put("email", usuario.getEmail());
            json.put("senha", usuario.getSenha());
            json.put("telefone", usuario.getTelefone());
            json.put("cpf", usuario.getCpf());
            json.put("privilegio", usuario.getPrivilegio());
            json.put("sexo", usuario.getSexo());
            json.put("cep", usuario.getCep());
            json.put("rua", usuario.getRua());
            json.put("bairro", usuario.getBairro());
            json.put("numero", usuario.getNumero());
            json.put("cidade", usuario.getCidade());
            json.put("estado", usuario.getEstado());
            return json;
        }
        return null;
    }

    public List<Map<String, Object>> onBuscar(String filtro) {
        //criar a conexao
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();
        List<Usuario> listaUsers;

        //mandar para a model
        listaUsers = usuarioModel.consultar(filtro, conexao);
        if (listaUsers!=null) {
            List<Map<String, Object>> listaJson = new ArrayList<>();
            for (int i = 0; i < listaUsers.size(); i++) {
                Map<String, Object> json = new HashMap<>();
                json.put("cod", listaUsers.get(i).getCod());
                json.put("nome", listaUsers.get(i).getNome());
                json.put("email", listaUsers.get(i).getEmail());
                json.put("senha", listaUsers.get(i).getSenha());
                json.put("telefone", listaUsers.get(i).getTelefone());
                json.put("cpf", listaUsers.get(i).getCpf());
                json.put("privilegio", listaUsers.get(i).getPrivilegio());
                json.put("sexo", listaUsers.get(i).getSexo());
                json.put("cep", listaUsers.get(i).getCep());
                json.put("rua", listaUsers.get(i).getRua());
                json.put("bairro", listaUsers.get(i).getBairro());
                json.put("numero", listaUsers.get(i).getNumero());
                json.put("cidade", listaUsers.get(i).getCidade());
                json.put("estado", listaUsers.get(i).getEstado());
                listaJson.add(json);
            }
            return listaJson; //para retornar um json corretamente para a "rest-view"
        }
        return null;
    }

    public boolean onAlterar(Map<String, Object> json) {
        if (validarAlterar(json)) {
            //criar a conexao
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            //setar valores para o usuario model
            usuarioModel.setCod(Integer.parseInt(json.get("cod").toString()));
            usuarioModel.setNome(json.get("nome").toString());
            usuarioModel.setEmail(json.get("email").toString());
            usuarioModel.setSenha(json.get("senha").toString());
            usuarioModel.setTelefone(json.get("telefone").toString());
            usuarioModel.setCpf(json.get("cpf").toString());
            usuarioModel.setPrivilegio(json.get("privilegio").toString());
            usuarioModel.setSexo(json.get("sexo").toString());
            usuarioModel.setCep(json.get("cep").toString());
            usuarioModel.setRua(json.get("rua").toString());
            usuarioModel.setBairro(json.get("bairro").toString());
            usuarioModel.setNumero(json.get("numero").toString());
            usuarioModel.setCidade(json.get("cidade").toString());
            usuarioModel.setEstado(json.get("estado").toString());
            return usuarioModel.alterar(conexao);
        }
        return false;
    }

    public boolean validar(Map<String, Object> json) {
        return !json.isEmpty() &&
                !json.get("nome").toString().isEmpty() &&
                !json.get("email").toString().isEmpty() &&
                !json.get("senha").toString().isEmpty() &&
                !json.get("telefone").toString().isEmpty() &&
                !json.get("cpf").toString().isEmpty() &&
                !json.get("privilegio").toString().isEmpty() &&
                !json.get("sexo").toString().isEmpty() &&
                !json.get("cep").toString().isEmpty() &&
                !json.get("rua").toString().isEmpty() &&
                !json.get("bairro").toString().isEmpty() &&
                !json.get("numero").toString().isEmpty() &&
                !json.get("cidade").toString().isEmpty() &&
                !json.get("estado").toString().isEmpty();
    }

    public boolean validarAlterar(Map<String, Object> json) {
        return validar(json) && Integer.parseInt(json.get("cod").toString()) > 0;
    }
}
