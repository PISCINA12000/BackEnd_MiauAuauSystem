package miau.auau.amigosdequatropatas.view;

import jakarta.servlet.http.HttpServletRequest;
import miau.auau.amigosdequatropatas.controller.AdocaoController;
import miau.auau.amigosdequatropatas.security.JWTTokenProvider;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/adocao")
public class AdocaoRestView {
    // DECLARAÇÕES
    // GRAVAR
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("gravar")
    public ResponseEntity<Object> gravarAnimal(
            @RequestParam int cod_animal,
            @RequestParam int cod_usuario,
            @RequestParam String data,
            @RequestParam String status){

        //criar o mapeamento do meu json ADOCAO
        Map<String, Object> json = new HashMap<>();
        json.put("animal", cod_animal);
        json.put("usuario", cod_usuario);
        json.put("data", data);
        json.put("status", status);

        AdocaoController adocaoController = new AdocaoController();
        if (adocaoController.onGravar(json))
            return ResponseEntity.ok(json);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar adocao!!"));
    }

    @PostMapping("solicitar")
    public ResponseEntity<Object> solicitarAnimal(
            @RequestParam int cod_animal,
            @RequestParam int cod_usuario,
            @RequestParam String data,
            @RequestParam String status){

        String token = httpServletRequest.getHeader("Authorization");
        if (JWTTokenProvider.verifyToken(token))
        {
            Map<String, Object> json = new HashMap<>();
            json.put("animal", cod_animal);
            json.put("usuario", cod_usuario);
            json.put("data", data);
            json.put("status", status);

            AdocaoController adocaoController = new AdocaoController();
            if (adocaoController.onGravar(json))
                return ResponseEntity.ok(json);
            else
                return ResponseEntity.badRequest().body(new Erro("Erro ao gravar adocao!!"));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Acesso não permitido"));

    }


    @GetMapping("buscarAno")
    public ResponseEntity<Object> getAnos()
    {
        List <String> listAnos;
        AdocaoController adocaoController = new AdocaoController();
        listAnos = adocaoController.onBuscarAno();
        if(listAnos != null)
            return ResponseEntity.ok().body(listAnos);
        else
            return ResponseEntity.badRequest().body(new Erro("Nenhuma adoção cadastrada!!"));
    }

    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getAdocao(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> listaJson;

        //mando para a controller
        AdocaoController adocaoController = new AdocaoController();
        listaJson = adocaoController.onBuscar(filtro);
        if(listaJson != null)
            return ResponseEntity.ok().body(listaJson);
        else
            return ResponseEntity.badRequest().body(new Erro("Adoção não encontrada ou nenhuma adoção cadastrada!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getAdocao(@PathVariable(value = "id") int codAdocao) {
        Map<String, Object> json;

        //mando para a controller
        AdocaoController adocaoController = new AdocaoController();
        json = adocaoController.onBuscarId(codAdocao);
        if(json != null) {
            return ResponseEntity.ok(json);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Adoção não encontrada!!"));
    }

    @DeleteMapping("excluir/{id}") //
    public ResponseEntity<Object> excluirAdocao(@PathVariable (value = "id") int codAdocao) {
        AdocaoController adocaoController = new AdocaoController();
        if(adocaoController.onDelete(codAdocao)) {
            return ResponseEntity.ok(new Erro("Adoção excluida com sucesso!"));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir adoção!!"));
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarAdocao(
            @RequestParam int codAdocao,
            @RequestParam int cod_usuario,
            @RequestParam int cod_animal,
            @RequestParam String data,
            @RequestParam String status)
    {
        Map<String, Object> json = new HashMap<>();
        json.put("codAdocao", codAdocao);
        json.put("animal", cod_animal);
        json.put("usuario", cod_usuario);
        json.put("data", data);
        json.put("status", status);
        AdocaoController adocaoController = new AdocaoController();
        if (adocaoController.onAlterar(json)) {
            return ResponseEntity.ok(json);
        } else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar adoção!!"));
    }
    @GetMapping("download-pdf/{id}")
    public ResponseEntity<Object> downloadPdf(@PathVariable (value = "id") int codAdocao) throws IOException
    {
        Map<String, Object> json = new HashMap<>();
        json.put("codAdocao", codAdocao);
        AdocaoController adocaoController = new AdocaoController();

        byte[] pdf = adocaoController.onGerarPdf(json);
        if (pdf != null && pdf.length > 0)
        {
            return ResponseEntity.ok().body(pdf);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gerar PDF!!"));

    }
}
