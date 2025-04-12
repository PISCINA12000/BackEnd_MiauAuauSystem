package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.UsuarioController;
import miau.auau.amigosdequatropatas.util.Erro;
import miau.auau.amigosdequatropatas.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/usuario")
public class UsuarioRestView
{
    @Autowired
    private UsuarioController usuarioController;

    @GetMapping("buscar/{filtro}") // vazio, retorna todos
    public ResponseEntity<Object> getUsuarios(@PathVariable(value = "filtro") String filtro)
    {
        List<Map<String, Object>> listaJson = new ArrayList<>();
        listaJson = usuarioController.onBuscar(filtro);
        if(!listaJson.isEmpty())
            return ResponseEntity.ok().body(listaJson);
        else
            return ResponseEntity.badRequest().body(new Erro("Usuario nao encontrado ou nenhum Usuario cadastrado!!"));
    }

    @GetMapping("buscar-id/{id}")
    public ResponseEntity<Object> getUsuarios(@PathVariable(value = "id") int id)
    {

        if(usuarioController.onBuscarId(id) != null)
        {
            return ResponseEntity.ok(usuarioController.onBuscarId(id));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Usuario não encontrado!"));

    }

    @PostMapping("gravar")
    public ResponseEntity<Object> gravarUsuario(@RequestBody Usuario usuario)
    {

        if (usuarioController.onGravar(usuario)) {
            return ResponseEntity.ok(usuario);
        }
        else {
            return ResponseEntity.badRequest().body(new Erro("Erro ao gravar usuario"));
        }
    }

    // DELETE
    @DeleteMapping("excluir/{id}") //
    public ResponseEntity<Object> excluirUsuario(@PathVariable (value = "id") int id) {

        if(usuarioController.onDelete(id))
        {
            return ResponseEntity.ok(new Erro("Usuario excluido com sucesso!"));
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao excluir usuario"));

    }

    @PutMapping("atualizar")
    public ResponseEntity<Object> atualizarUsuario(@RequestBody Usuario usuario)
    {
        if(usuarioController.onAlterar(usuario))
        {
            return ResponseEntity.ok(usuario);
        }
        else
            return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar usuario!"));
    }
}