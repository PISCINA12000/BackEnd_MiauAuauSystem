package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.TipoLancamentoController;
import miau.auau.amigosdequatropatas.entities.TipoLancamento;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/tipo-lancamento")
public class TipoLancamentoRestView {
    // DECLARAÇÕES
    @Autowired
    private TipoLancamentoController tipoLancamentoController;

    // BUSCAR
    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getTiposLancamento(@PathVariable(value = "filtro") String filtro) {
        if (!tipoLancamentoController.onBuscar(filtro).isEmpty())
            return ResponseEntity.ok().body(tipoLancamentoController.onBuscar(filtro));
        else
            return ResponseEntity.badRequest().body(new Erro("Tipo de lançamento não encontrado ou nenhum cadastrado"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getTipoLancamento(@PathVariable(value = "id") int id) {
        if (tipoLancamentoController.onBuscarId(id) != null) {
            return ResponseEntity.ok(tipoLancamentoController.onBuscarId(id));
        } else
            return ResponseEntity.badRequest().body(new Erro("Tipo de lançamento não encontrado!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarTipoLancamento(@RequestParam String descricao) {
        //criar um JSON
        Map<String, Object> json = new HashMap<>();
        json.put("descricao", descricao);

        //mandar para a controller
        if (tipoLancamentoController.onGravar(json))
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar tipo de lançamento"));
    }

    // DELETE
    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> excluirTipoLancamento(@PathVariable(value = "id") int id) {
        if (tipoLancamentoController.onDelete(id)) {
            return ResponseEntity.ok(new Erro("Tipo de lançamento excluído com sucesso!"));
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir tipo de lançamento!!"));
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarTipoLancamento(
            @RequestParam int cod,
            @RequestParam String descricao)
    {
        Map<String, Object> json = new HashMap<>();
        json.put("cod", cod);
        json.put("descricao", descricao);

        //mandar para a controller
        if (tipoLancamentoController.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar tipo de lançamento!!"));
    }
}
