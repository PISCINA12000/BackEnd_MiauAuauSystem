package miau.auau.amigosdequatropatas.view;
import miau.auau.amigosdequatropatas.controller.AgendarMedicamentoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/agendar-medicamento")
public class AgendarMedicamentoRestView {

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAgendamento(
            @RequestParam int animal,
            @RequestParam int tipo_medicamento,
            @RequestParam int intervalo,
            @RequestParam String formato,
            @RequestParam int periodo) {

        Map<String, Object> json = new HashMap<>();
        json.put("animal", animal);
        json.put("medicamento", tipo_medicamento);
        json.put("intervalo", intervalo);
        json.put("formato", formato);
        json.put("periodo", periodo);

        AgendarMedicamentoController controller = new AgendarMedicamentoController();
        if (controller.onGravar(json)) {
            return ResponseEntity.ok(json);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar agendamento de medicamento!!"));
        }
    }

    // vazio, retorna todos
    @GetMapping("buscar/{filtro}")
    public ResponseEntity<Object> getAgendamentos(@PathVariable(value = "filtro") String filtro) {

        //mando para a controller
        AgendarMedicamentoController controller = new AgendarMedicamentoController();
        List<Map<String, Object>> listaJson = controller.onBuscar(filtro);

        if (listaJson != null) {
            return ResponseEntity.ok(listaJson);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Nenhum agendamento encontrado!!"));
        }
    }

    // BUSCAR POR ID
    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getAgendamentoById(@PathVariable(value = "id") int codAgendarMedicamento) {
        //mando para a controller
        AgendarMedicamentoController controller = new AgendarMedicamentoController();
        Map<String, Object> json = controller.onBuscarId(codAgendarMedicamento);

        if (json != null) {
            return ResponseEntity.ok(json);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Agendamento não encontrado!!"));
        }
    }


    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> excluirAgendamento(@PathVariable(value = "id") int codAgendarMedicamento) {
        AgendarMedicamentoController controller = new AgendarMedicamentoController();

        if (controller.onDelete(codAgendarMedicamento)) {
            return ResponseEntity.ok(new Erro("Agendamento excluído com sucesso!"));
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir agendamento!!"));
        }
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAgendamento(
            @RequestParam int codAgendarMedicamento,
            @RequestParam int animal,
            @RequestParam int medicamento,
            @RequestParam int intervalo,
            @RequestParam String formato,
            @RequestParam int periodo) {

        Map<String, Object> json = new HashMap<>();
        json.put("codAgendarMedicamento", codAgendarMedicamento);
        json.put("animal", animal);
        json.put("medicamento", medicamento);
        json.put("intervalo", intervalo);
        json.put("formato", formato);
        json.put("periodo", periodo);

        AgendarMedicamentoController controller = new AgendarMedicamentoController();
        if (controller.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar agendamento!!"));
        }
    }
}

