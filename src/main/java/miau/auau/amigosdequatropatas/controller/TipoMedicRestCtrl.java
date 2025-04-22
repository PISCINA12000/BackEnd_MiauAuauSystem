package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.model.Erro;
import miau.auau.amigosdequatropatas.model.TipoMedicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("apis/tipo_medic")
public class TipoMedicRestCtrl {

    @Autowired
    private TipoMedicamento tpMedicamento;
    // MÉTODOS ---------------------------------------------
    // BUSCAR
    @GetMapping("buscar")
    public ResponseEntity<Object> getTpMedic() {
        List<TipoMedicamento> lista = new ArrayList<>();
        // busca todos os tipos de medicamento e atribui para 'lista'
        if(lista.size() > 0){
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.badRequest().body(new Erro("Nenhum Tipo de Medicamento cadastrado!"));
    }

    @GetMapping("buscar/{tipoMedic}")
    public ResponseEntity<Object> getTpMeidic(String tipoMedic) {
        TipoMedicamento tipoMedicamento = new TipoMedicamento();
        // busca o Tipo de Medicamento que corresponde com tipomedic e retorna para 'tipoMedicamento'
        if(tipoMedicamento != null){
            return ResponseEntity.ok(tipoMedicamento);
        }
        return ResponseEntity.badRequest().body(new Erro("Esse Tipo de Medicamento não foi encontrado!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarTpMedic(@RequestBody TipoMedicamento tpMedic) {
        // grava no banco de dados o respectivo objeto 'tpMedic'
        return ResponseEntity.ok(tpMedic); // depois da gravação bem sucedida
    }

    // DELETE
    @DeleteMapping("excluir")
    public ResponseEntity<Object> excluirTpMedic(int id) {
        String tpMedic = "";
        // deleta do banco de dados o Tipo com o 'cod' == 'id' e atribui seu nome para 'tpMedic'
        return ResponseEntity.ok("O Tipo de Medicamento " + tpMedic + " foi excluído com sucesso!");
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAnimal(@RequestBody TipoMedicamento tpMedic) {
        // atualizar no banco de dados o Tipo de Medicamento com o mesmo id do objeto 'tpMedic'
        return ResponseEntity.ok("O Tipo de Medicamento foi atualizado com sucesso!");
    }
}
