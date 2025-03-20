package miau.auau.amigosdequatropatas.restcontrollers;

import miau.auau.amigosdequatropatas.entidades.Erro;
import miau.auau.amigosdequatropatas.entidades.TipoLancamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("apis/tipo_lanc")
public class TipoLancRestCtrl {
    // DECLARAÇÕES

    // MÉTODOS ---------------------------------------------
    // BUSCAR
    @GetMapping("buscar")
    public ResponseEntity<Object> getTpLanc() {
        List<TipoLancamento> lista = new ArrayList<>();
        // busca todos os tipos de lancamento e atribui para 'lista'
        if(lista.size() > 0){
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.badRequest().body(new Erro("Nenhum Tipo de Lançamento cadastrado!"));
    }
    @GetMapping("buscar")
    public ResponseEntity<Object> getTpLanc(String tipoLanc) {
        TipoLancamento tipoLancamento = new TipoLancamento();
        // busca o Tipo de Lançamento que corresponde com tipoLanc e retorna para 'tipoLancamento'
        if(tipoLancamento != null){
            return ResponseEntity.ok(tipoLancamento);
        }
        return ResponseEntity.badRequest().body(new Erro("Esse Tipo de Lançamento não foi encontrado!"));
    }

    // GRAVAR
    @PostMapping("gravar")
    public ResponseEntity<Object> gravarTpLanc(@RequestBody TipoLancamento tpLanc) {
        // grava no banco de dados o respectivo objeto 'tpLanc'
        return ResponseEntity.ok(tpLanc); // depois da gravação bem sucedida
    }

    // DELETE
    @DeleteMapping("excluir")
    public ResponseEntity<Object> excluirTpLanc(int id) {
        String tpLanc = "";
        // deleta do banco de dados o Tipo com o 'cod' == 'id' e atribui seu nome para 'tpLanc'
        return ResponseEntity.ok("O Tipo de Lançamento " + tpLanc + " foi excluído com sucesso!");
    }

    // ATUALIZAR
    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAnimal(@RequestBody TipoLancamento tpLanc) {
        // atualizar no banco de dados o Tipo Lançamento com o mesmo id do objeto 'tpLanc'
        return ResponseEntity.ok("O Tipo de Lançamento foi atualizado com sucesso!");
    }
}
