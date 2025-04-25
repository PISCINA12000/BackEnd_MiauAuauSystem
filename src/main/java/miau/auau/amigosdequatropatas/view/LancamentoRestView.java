package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.LancamentoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/lancamento")
public class LancamentoRestView {
    @Autowired
    private LancamentoController lancController;

    @GetMapping("buscar/{filtro}")
    public ResponseEntity<Object> getLancamento(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> lista;

        //mando para a controller
        lista = lancController.onBuscar(filtro);
        if(lista!=null && !lista.isEmpty())
            return ResponseEntity.ok().body(lista);
        return ResponseEntity.badRequest().body(new Erro("Não foi possível recuperar os lançamentos!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getLancamento(@PathVariable(value = "id") int id) {
        Map<String, Object> json;

        //mando para a controller
        json = lancController.onBuscarID(id);
        if(json!=null)
            return ResponseEntity.ok().body(json);
        return ResponseEntity.badRequest().body(new Erro("Não encontrado lançamento com esse ID!!"));
    }

    @GetMapping("arquivo/{id}")
    public ResponseEntity<byte[]> getArquivoPDF(@PathVariable(value = "id") int id) {
        byte[] pdfBytes = lancController.onBuscarPDF(id); // view chama controller

        if (pdfBytes != null && pdfBytes.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline()
                    .filename("lancamento_" + id + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("gravar")
    public ResponseEntity<Object> postLancamento(
            @RequestParam String data,
            @RequestParam int codTpLanc,
            @RequestParam int codAnimal,
            @RequestParam int debito,
            @RequestParam int credito,
            @RequestParam String descricao,
            @RequestParam double valor,
            @RequestParam (value="pdf", required = false) MultipartFile pdf)
    {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("data", data);
            json.put("codTpLanc", codTpLanc);
            json.put("codAnimal", codAnimal);
            json.put("debito", debito);
            json.put("credito", credito);
            json.put("descricao", descricao);
            json.put("valor", valor);

            //agora tratar o pdf para um arquivo binário para conseguir passar para a minha controller
            //posso atribuir nulo para a chave pois o pdf é algo opcional
            if(pdf!=null && !pdf.isEmpty()) {
                byte[] arquivo = pdf.getBytes();
                json.put("arquivo", arquivo);
            }
            else
                json.put("arquivo", null);

            //mando para a controller
            if (lancController.onGravar(json))
                return ResponseEntity.ok().body(json);
            return ResponseEntity.badRequest().body(new Erro("Não foi possível GRAVAR o Lançamento!!"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(
                    new Erro("Não foi possível GRAVAR o Lançamento e entrou na exceção!!"+ e.getMessage()));
        }
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> putLancamento(
            @RequestParam int id,
            @RequestParam String data,
            @RequestParam int codTpLanc,
            @RequestParam int codAnimal,
            @RequestParam int debito,
            @RequestParam int credito,
            @RequestParam String descricao,
            @RequestParam double valor,
            @RequestParam (value="pdf", required = false) MultipartFile pdf)
    {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("id", id);
            json.put("data", data);
            json.put("codTpLanc", codTpLanc);
            json.put("codAnimal", codAnimal);
            json.put("debito", debito);
            json.put("credito", credito);
            json.put("descricao", descricao);
            json.put("valor", valor);

            //agora tratar o pdf para um arquivo binário para conseguir passar para a minha controller
            //posso atribuir nulo para a chave pois o pdf é algo opcional
            if(pdf!=null && !pdf.isEmpty()) {
                byte[] arquivo = pdf.getBytes();
                json.put("arquivo", arquivo);
            }
            else
                json.put("arquivo", null);

            //mando para a controller
            if (lancController.onAtualizar(json))
                return ResponseEntity.ok().body(json);
            return ResponseEntity.badRequest().body(new Erro("Não foi possível ATUALIZAR o Lançamento!!"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(
                    new Erro("Não foi possível ATUALIZAR o Lançamento e entrou na exceção!! "+ e.getMessage()));
        }
    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> deleteLancamento(@PathVariable(value="id") int id) {
        //mandando para a controller
        if(lancController.onDelete(id))
            return ResponseEntity.ok().body(new Erro("Lançamento excluído com sucesso!"));
        return ResponseEntity.badRequest().body(new Erro("Não foi possível excluir o Lançamento!!"));
    }
}
