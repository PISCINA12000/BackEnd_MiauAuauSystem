package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.UsuarioController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/usuario")
public class UsuarioRestView {
    @Autowired
    private UsuarioController usuarioController;

    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getUsuarios(@PathVariable(value = "filtro") String filtro) {
        List<Map<String, Object>> listaJson;
        listaJson = usuarioController.onBuscar(filtro);
        if(!listaJson.isEmpty())
            return ResponseEntity.ok().body(listaJson);
        return ResponseEntity.badRequest().body(new Erro("Usuario nao encontrado ou nenhum Usuario cadastrado!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getUsuarios(@PathVariable(value = "id") int id) {
        Map<String, Object> json = usuarioController.onBuscarId(id);
        if(json != null)
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Usuario não encontrado!!"));
    }

    @PostMapping("gravar")
    public ResponseEntity<Object> gravarUsuario(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String telefone,
            @RequestParam String cpf,
            @RequestParam String privilegio,
            @RequestParam String sexo,
            @RequestParam String cep,
            @RequestParam String rua,
            @RequestParam String bairro,
            @RequestParam String numero)
    {
        Map<String, Object> json = new HashMap<>();
        json.put("nome", nome);
        json.put("email", email);
        json.put("senha", senha);
        json.put("telefone", telefone);
        json.put("cpf", cpf);
        json.put("privilegio", privilegio);
        json.put("sexo", sexo);
        json.put("cep", cep);
        json.put("rua", rua);
        json.put("bairro", bairro);
        json.put("numero", numero);

        //mandar para a controller
        if (usuarioController.onGravar(json))
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Erro ao gravar usuário!!"));
    }

    // DELETE
    @DeleteMapping("excluir/{id}") //
    public ResponseEntity<Object> excluirUsuario(@PathVariable (value = "id") int id) {
        if(usuarioController.onDelete(id))
            return ResponseEntity.ok(new Erro("Usuário excluído com sucesso!"));
        return ResponseEntity.badRequest().body(new Erro("Erro ao excluir usuário!!"));
    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarUsuario(
            @RequestParam int cod,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String telefone,
            @RequestParam String cpf,
            @RequestParam String privilegio,
            @RequestParam String sexo,
            @RequestParam String cep,
            @RequestParam String rua,
            @RequestParam String bairro,
            @RequestParam String numero)
    {
        //criar o json
        Map<String, Object> json = new HashMap<>();
        json.put("cod", cod);
        json.put("nome", nome);
        json.put("email", email);
        json.put("senha", senha);
        json.put("telefone", telefone);
        json.put("cpf", cpf);
        json.put("privilegio", privilegio);
        json.put("sexo", sexo);
        json.put("cep", cep);
        json.put("rua", rua);
        json.put("bairro", bairro);
        json.put("numero", numero);

        //mandar para a controller
        if(usuarioController.onAlterar(json))
            return ResponseEntity.ok(json);
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar usuario!!"));
    }
}