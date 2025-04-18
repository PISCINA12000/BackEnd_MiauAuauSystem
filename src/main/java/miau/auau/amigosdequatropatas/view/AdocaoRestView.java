package miau.auau.amigosdequatropatas.view;
import miau.auau.amigosdequatropatas.controller.AdocaoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/adocao")
public class AdocaoRestView {
    // DECLARAÇÕES
    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAnimal(
            @RequestParam int cod_animal,
            @RequestParam int cod_usuario,
            @RequestParam String data,
            @RequestParam String status){
        //criar o mapeamento do meu json ANIMAL
        //Animal animal = objectMapper.readValue(animalJson, Animal.class);
        //Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);

        Map<String, Object> json = new HashMap<>();
        json.put("animal", cod_animal);
        json.put("usuario", cod_usuario);
        json.put("data", data);
        json.put("status", status);
        AdocaoController adocaoController = new AdocaoController();
        if (adocaoController.onGravar(json))
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar adocao!!"));
    }
    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getAdocao(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> listaJson;

        //mando para a controller
        AdocaoController adocaoController = new AdocaoController();
        listaJson = adocaoController.onBuscar(filtro);
        if(listaJson != null)
            return ResponseEntity.ok().body(listaJson);
        else
            return ResponseEntity.badRequest().body(new Erro("Adoção não encontrada ou nenhuma adoção cadastrada!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getAdocao(@PathVariable(value = "id") int codAdocao) {
        Map<String, Object> json;

        //mando para a controller
        AdocaoController adocaoController = new AdocaoController();
        json = adocaoController.onBuscarId(codAdocao);
        if(json != null) {
            return ResponseEntity.ok(json);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Adoção não encontrada!!"));
    }

    @DeleteMapping("excluir/{id}") //
    public ResponseEntity<Object> excluirAdocao(@PathVariable (value = "id") int codAdocao) {
        AdocaoController adocaoController = new AdocaoController();
        if(adocaoController.onDelete(codAdocao)) {
            return ResponseEntity.ok(new Erro("Adoção excluida com sucesso!"));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir adoção!!"));
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAdocao(
            @RequestParam int codAdocao,
            @RequestParam int cod_usuario,
            @RequestParam int cod_animal,
            @RequestParam String data,
            @RequestParam String status)
             {

        Map<String, Object> json = new HashMap<>();
        json.put("codAdocao", codAdocao);
        json.put("animal", cod_animal);
        json.put("usuario", cod_usuario);
        json.put("data", data);
        json.put("status", status);

        AdocaoController adocaoController = new AdocaoController();
        if (adocaoController.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar adoção!!"));
    }

}
