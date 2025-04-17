package miau.auau.amigosdequatropatas.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miau.auau.amigosdequatropatas.controller.AdocaoController;
import miau.auau.amigosdequatropatas.entities.Animal;
import miau.auau.amigosdequatropatas.entities.Usuario;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/adocao")
public class AdocaoRestView {
    // DECLARAÇÕES
    private final ObjectMapper objectMapper = new ObjectMapper();
    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAnimal(
            @RequestParam Animal animal,
            @RequestParam Usuario usuario,
            @RequestParam String data){
        //criar o mapeamento do meu json ANIMAL
        //Animal animal = objectMapper.readValue(animalJson, Animal.class);
        //Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);

        Map<String, Object> json = new HashMap<>();
        json.put("animal", animal);
        json.put("usuario", usuario);
        json.put("data", data);
        AdocaoController adocaoController = new AdocaoController();
        if (adocaoController.onGravar(json))
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar adocao!!"));
    }


}
