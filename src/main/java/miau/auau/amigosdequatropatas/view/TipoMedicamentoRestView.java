package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.TipoMedicamentoController;
import miau.auau.amigosdequatropatas.entities.TipoMedicamento;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        if (!tipoMedicamentoController.onBuscar(filtro).isEmpty())
            return ResponseEntity.ok().body(tipoMedicamentoController.onBuscar(filtro));
        else
            return ResponseEntity.badRequest().body(new Erro("Tipo de medicamento não encontrado ou nenhum cadastrado!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getTipoMedicamento(@PathVariable(value = "id") int id) {
        if (tipoMedicamentoController.onBuscarId(id) != null) {
            return ResponseEntity.ok(tipoMedicamentoController.onBuscarId(id));
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