package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.PlanoContasGerencialController;
import miau.auau.amigosdequatropatas.controller.PlanoContasReferencialController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/plano-contas-referencial")
public class PlanoContasReferencialRestView {
    @Autowired
    private PlanoContasReferencialController planoContasReferencialController;

    @GetMapping("buscar/{filtro}")
    public ResponseEntity<Object> getFiltro(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> listaJson;

        listaJson = planoContasReferencialController.onBuscar(filtro);
        if (listaJson != null)
            return ResponseEntity.ok().body(listaJson);
        return ResponseEntity.badRequest().body(new Erro("Tipo de pagamento não encontrado ou nenhum cadastrado!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getID(@PathVariable(value = "id") int id) {
        Map<String, Object> json;

        json = planoContasReferencialController.onBuscarId(id);
        if (json != null)
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Tipo de pagamento não encontrado!!"));
    }

    @PostMapping("gravar")
    public ResponseEntity<Object> gravarTipoLancamento(
            @RequestParam String descricao,
            @RequestParam String natureza,
            @RequestParam String classificacao) {
        Map<String, Object> json = new HashMap<>();
        json.put("descricao", descricao);
        json.put("natureza", natureza);
        json.put("classificacao", classificacao);

        if (planoContasReferencialController.onGravar(json))
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Erro ao gravar tipo de pagamento!!"));
    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> excluirTipoLancamento(@PathVariable(value = "id") int id) {
        if (planoContasReferencialController.onDelete(id))
            return ResponseEntity.ok(new Erro("Tipo de pagamento excluído com sucesso!"));
        return ResponseEntity.badRequest().body(new Erro("Erro ao excluir tipo de pagamento!!"));
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarTipoLancamento(
            @RequestParam int cod,
            @RequestParam String descricao,
            @RequestParam String natureza,
            @RequestParam String classificacao) {
        Map<String, Object> json = new HashMap<>();
        json.put("cod", cod);
        json.put("descricao", descricao);
        json.put("natureza", natureza);
        json.put("classificacao", classificacao);

        if (planoContasReferencialController.onAlterar(json))
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar tipo de pagamento!!"));
    }
}
