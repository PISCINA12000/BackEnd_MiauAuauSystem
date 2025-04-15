package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.TipoMedicamentoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/tipo-medicamento")
public class TipoMedicamentoRestView {
    // DECLARAÇÕES
    @Autowired
    private TipoMedicamentoController tipoMedicamentoController;

    // BUSCAR
    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getTiposMedicamento(@PathVariable(value = "filtro") String filtro) {
        //instancio uma lista de JSON
        List<Map<String, Object>> listaJson;

        //mando para a controller
        listaJson = tipoMedicamentoController.onBuscar(filtro);
        if (listaJson!=null)
            return ResponseEntity.ok().body(listaJson);
        else
            return ResponseEntity.badRequest().body(new Erro("Tipo de medicamento não encontrado ou nenhum cadastrado!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getTipoMedicamento(@PathVariable(value = "id") int id) {
        Map<String, Object> json;

        //mando para a controller
        json = tipoMedicamentoController.onBuscarId(id);
        if (json != null) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Tipo de medicamento não encontrado!!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarTipoMedicamento(@RequestParam String nome) {
        //criar o json
        Map<String, Object> json = new HashMap<>();
        json.put("nome", nome);

        //mandar para a controller
        if (tipoMedicamentoController.onGravar(json))
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar tipo de medicamento!!"));
    }

    // DELETE
    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> excluirTipoMedicamento(@PathVariable(value = "id") int id) {
        if (tipoMedicamentoController.onDelete(id)) {
            return ResponseEntity.ok(new Erro("Tipo de medicamento excluído com sucesso!"));
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir tipo de medicamento!!"));
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarTipoMedicamento(
            @RequestParam int cod,
            @RequestParam String nome)
    {
        //criar o JSON
        Map<String, Object> json = new HashMap<>();
        json.put("cod", cod);
        json.put("nome", nome);

        //mandar para a controller
        if (tipoMedicamentoController.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar tipo de medicamento"));
    }
}