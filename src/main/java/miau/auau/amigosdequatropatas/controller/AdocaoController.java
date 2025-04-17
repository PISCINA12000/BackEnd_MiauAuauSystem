package miau.auau.amigosdequatropatas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miau.auau.amigosdequatropatas.dao.AdocaoDAO;
import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.UsuarioDAO;
import miau.auau.amigosdequatropatas.entities.Adocao;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdocaoController {


    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean onGravar(Map<String, Object> json) {
        System.out.println(json);
        if (validar(json)) {
            //criar aqui a conexão para o banco de dados
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            // Converter os objetos JSON em objetos das entidades
            Animal animal = objectMapper.convertValue(json.get("animal"), Animal.class);
            Usuario usuario = objectMapper.convertValue(json.get("usuario"), Usuario.class);
            String data = json.get("data").toString();

            Adocao adocao = new Adocao(animal, usuario, data);

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




    public boolean validar(Map<String, Object> json) {
        //retorna verdade se todas as informações forem válidas
        if (json != null && json.containsKey("data") && json.containsKey("usuario") && json.containsKey("animal"))
        {
            SingletonDB singletonDB = SingletonDB.getInstance();
            Conexao conexao = singletonDB.getConexao();

            Animal animal = objectMapper.convertValue(json.get("animal"), Animal.class);
            Usuario usuario = objectMapper.convertValue(json.get("usuario"), Usuario.class);

            // Verificar se os objetos existem no banco
            AnimalDAO animalDAO = new AnimalDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            animal = animalDAO.get(animal.getCodAnimal(), conexao);
            usuario = usuarioDAO.get(usuario.getCod(), conexao);
            if (usuario != null && animal != null)
                return true;
        }

        return false;
    }


}
