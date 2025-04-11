package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.AnimalController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/animal")
public class AnimalRestView {
    // DECLARAÇÕES
    @Autowired
    private AnimalController animalController;

    // BUSCAR
    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getAnimais(@PathVariable(value = "filtro") String filtro) {
        if(!animalController.onBuscar(filtro).isEmpty())
            return ResponseEntity.ok().body(animalController.onBuscar(filtro));
        else
            return ResponseEntity.badRequest().body(new Erro("Animal não encontrado ou nenhum animal cadastrado"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getAnimais(@PathVariable(value = "id") int id) {
        if(animalController.onBuscarId(id) != null) {
            return ResponseEntity.ok(animalController.onBuscarId(id));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Animal não encontrado!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAnimal(
            @RequestParam String nome,
            @RequestParam String sexo,
            @RequestParam String raca,
            @RequestParam int idade,
            @RequestParam double peso,
            @RequestParam String castrado,
            @RequestParam String adotado,
            @RequestParam String imagemBase64)
    {
        //criar o mapeamento do meu json ANIMAL
        Map<String, Object> json = new HashMap<>();
        json.put("nome", nome);
        json.put("sexo", sexo);
        json.put("raca", raca);
        json.put("idade", idade);
        json.put("peso", peso);
        json.put("castrado", castrado);
        json.put("adotado", adotado);
        json.put("imagemBase64", imagemBase64);

        if (animalController.onGravar(json)) //json -> enviar
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar animal"));
    }

    // DELETE
    @DeleteMapping("excluir/{id}") //
    public ResponseEntity<Object> excluirAnimal(@PathVariable (value = "id") int id) {
       if(animalController.onDelete(id)) {
           return ResponseEntity.ok(new Erro("Animal excluido com sucesso!"));
       }
       else
           return ResponseEntity.badRequest().body(new Erro("Erro ao excluir animal!!"));
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAnimal(
            @RequestParam int id,
            @RequestParam String nome,
            @RequestParam String sexo,
            @RequestParam String raca,
            @RequestParam int idade,
            @RequestParam double peso,
            @RequestParam String castrado,
            @RequestParam String adotado,
            @RequestParam String imagemBase64)
    {
        //criar o mapeamento do meu json ANIMAL
        Map<String, Object> json = new HashMap<>();
        json.put("codAnimal", id);
        json.put("nome", nome);
        json.put("sexo", sexo);
        json.put("raca", raca);
        json.put("idade", idade);
        json.put("peso", peso);
        json.put("castrado", castrado);
        json.put("adotado", adotado);
        json.put("imagemBase64", imagemBase64);

        if (animalController.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar animal"));
    }
}
