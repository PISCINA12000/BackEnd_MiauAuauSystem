package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.AgendarMedicamentoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/agendar-medicamento")
public class AgendarMedicamentoRestView {

    @Autowired
    private AgendarMedicamentoController agendarMedController;


    // vazio, retorna todos
    @GetMapping("buscar/{filtro}")
    public ResponseEntity<Object> getAgendamentos(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> listaJson;

        // mando para a controller
         listaJson= agendarMedController.onBuscar(filtro);

        if (listaJson != null)
            return ResponseEntity.ok(listaJson);
        return ResponseEntity.badRequest().body(new Erro("Nenhum agendamento encontrado!!"));
    }

    // BUSCAR POR ID
    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getAgendamentoById(@PathVariable(value = "id") int codAgendarMedicamento) {
        Map<String, Object> json;

        // mando para a controller
        json = agendarMedController.onBuscarId(codAgendarMedicamento);

        if (json != null)
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Agendamento não encontrado!!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAgendamento(
            @RequestParam int animal,
            @RequestParam int medicamento,
            @RequestParam String dataAplicacao,
            @RequestParam boolean status) {
        try{
            Map<String, Object> json = new HashMap<>();
            json.put("animal", animal);
            json.put("medicamento", medicamento);
            json.put("dataAplicacao", dataAplicacao);
            json.put("status", status);


            //mando para a controller
            if (agendarMedController.onGravar(json))
                return ResponseEntity.ok(json);
            return ResponseEntity.badRequest().body(new Erro("Não foi possivel gravar agendamento de medicamento!!"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(
                    new Erro("Não foi possível GRAVAR o agendamento e entrou na exceção!!"+ e.getMessage()));
        }
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAgendamento(
            @RequestParam int codAgendarMedicamento,
            @RequestParam int animal,
            @RequestParam int medicamento,
            @RequestParam String dataAplicacao,
            @RequestParam boolean status) {

        Map<String, Object> json = new HashMap<>();
        json.put("codAgendarMedicamento", codAgendarMedicamento);
        json.put("animal", animal);
        json.put("medicamento", medicamento);
        json.put("dataAplicacao", dataAplicacao);
        json.put("status", status);


        //mando para a controller
        if (agendarMedController.onAlterar(json))
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar agendamento!!"));

    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> excluirAgendamento(@PathVariable(value = "id") int codAgendarMedicamento) {
        //mando para a controller
        if (agendarMedController.onDelete(codAgendarMedicamento))
            return ResponseEntity.ok(new Erro("Agendamento excluído com sucesso!"));
        return ResponseEntity.badRequest().body(new Erro("Erro ao excluir agendamento!!"));
    }


}