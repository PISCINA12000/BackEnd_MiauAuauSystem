package miau.auau.amigosdequatropatas.view;


import miau.auau.amigosdequatropatas.controller.AdocaoController;
import miau.auau.amigosdequatropatas.controller.ProntuarioController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/prontuario")
public class ProntuarioRestView {


    @Autowired
    private ProntuarioController pronController;


    //filtrar registro pelo tipo do registro ou nao e ordena pela data com filtro ou n
    @GetMapping("buscar/{filtro}")
    public ResponseEntity<Object> getProntuario(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> lista;

        //mando para a controller
        lista = pronController.onBuscar(filtro);
        if(lista!=null && !lista.isEmpty())
            return ResponseEntity.ok().body(lista);
        return ResponseEntity.badRequest().body(new Erro("Não foi possível recuperar os registros do prontuario!!"));
    }

    //retorna registros de um periodo
    @GetMapping("buscar")
    public ResponseEntity<Object> getProntuario(@RequestParam String dataIni, @RequestParam String dataFim) {
        List<Map<String, Object>> lista;

        //mando para a controller
        lista = pronController.onBuscarData(dataIni, dataFim);
        if(lista!=null && !lista.isEmpty())
            return ResponseEntity.ok().body(lista);
        return ResponseEntity.badRequest().body(new Erro("Não existe registros dentro desse período!!"));
    }

    //retorna todos os registros de um animal
    @GetMapping("buscar_animal/{id}")
    public ResponseEntity<Object> getAnimal(@PathVariable(value = "id") int id) {
        List<Map<String, Object>> lista;

        //mando para a controller
        lista = pronController.onBuscarPorIdAnimal(id);
        if(lista!=null /*&& !lista.isEmpty()*/)
            return ResponseEntity.ok().body(lista);
        return ResponseEntity.badRequest().body(new Erro("Não encontrado registros nesse ID do animal!!"));
    }
    //retorna registro com id
    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getIdProntuario(@PathVariable(value = "id") int id) {
        Map<String, Object> json;

        //mando para a controller
        json = pronController.onBuscarId(id);
        if(json != null) {
            return ResponseEntity.ok(json);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Animal não encontrado!!"));
    }



    @GetMapping("arquivo/{id}")
    public ResponseEntity<byte[]> getArquivoPDF(@PathVariable(value = "id") int id) {
        byte[] pdfBytes = pronController.onBuscarPDF(id); // view chama controller

        if (pdfBytes != null && pdfBytes.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline()
                    .filename("prontuario_" + id + ".pdf")
                    .build());

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("gravar")
    public ResponseEntity<Object> postProntuario(
            @RequestParam int codAnimal,
            @RequestParam String data,
            @RequestParam String tipoRegistro,
            @RequestParam String observacao,
            @RequestParam (value="pdf", required = false) MultipartFile pdf)
    {
        try{
            Map<String, Object> json = new HashMap<>();
            json.put("codAnimal",codAnimal);
            json.put("data",data);
            json.put("tipoRegistro",tipoRegistro);
            json.put("observacao",observacao);

            //agora tratar o pdf para um arquivo binário para conseguir passar para a minha controller
            //posso atribuir nulo para a chave pois o pdf é algo opcional
            if(pdf != null && !pdf.isEmpty()){
                byte[] arquivo = pdf.getBytes();
                json.put("arquivo",arquivo);
            }
            else
                json.put("arquivo",null);


            //mando para controller
            if(pronController.onGravar(json))
                return ResponseEntity.ok().body(json);
            return ResponseEntity.badRequest().body(new Erro("Não foi possível GRAVAR o registro no prontuário!!"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(
                new Erro("Não foi possível GRAVAR o registro no prontuário e entrou na exceção!!"+ e.getMessage()));
        }
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> putProntuario(
            @RequestParam int cod,
            @RequestParam int codAnimal,
            @RequestParam String data,
            @RequestParam String tipoRegistro,
            @RequestParam String observacao,
            @RequestParam (value="pdf", required = false) MultipartFile arq)
    {
        try {
            Map<String, Object> json = new HashMap<>();
            json.put("cod",cod);
            json.put("codAnimal",codAnimal);
            json.put("data",data);
            json.put("tipoRegistro",tipoRegistro);
            json.put("observacao",observacao);

            //agora tratar o pdf para um arquivo binário para conseguir passar para a minha controller
            //posso atribuir nulo para a chave pois o pdf é algo opcional
            if(arq != null && !arq.isEmpty()){
                byte[] arquivo = arq.getBytes();
                json.put("arquivo",arquivo);
            }
            else
                json.put("arquivo",null);

            //mando para a controller
            if (pronController.onAtualizar(json))
                return ResponseEntity.ok().body(json);
            return ResponseEntity.badRequest().body(new Erro("Não foi possível ATUALIZAR o registro do prontuário!!"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(
                    new Erro("Não foi possível ATUALIZAR o registro do prontuário e entrou na exceção!! "+ e.getMessage()));
        }
    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<Object> deleteLancamento(@PathVariable(value="id") int id) {
        //mandando para a controller
        if(pronController.onDelete(id))
            return ResponseEntity.ok().body(new Erro("Registro do prontuário excluído com sucesso!"));
        return ResponseEntity.badRequest().body(new Erro("Não foi possível excluir o Registro do prontuário!!"));
    }

    @GetMapping("pdf/{codAnimal}")
    public ResponseEntity<Object> downloadPdf(@PathVariable (value = "codAnimal") int codAnimal) throws IOException
    {
        Map<String, Object> json = new HashMap<>();
        json.put("codAnimal", codAnimal);

        byte[] pdf = pronController.onGerarPdf(json);
        if (pdf != null && pdf.length > 0)
        {
            return ResponseEntity.ok().body(pdf);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao gerar PDF!!"));
    }


}
