package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.AcessoController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("autenticacao")
public class AcessoRestView
{
    @PostMapping
    public ResponseEntity<Object> autenticar (@RequestParam String email, @RequestParam String senha)
    {
        String token;
        AcessoController acessoController = new AcessoController();
        token = acessoController.autenticar(email, senha);
        if (token != null)
        {
            return ResponseEntity.ok(token);
        }
        else
        {
            return ResponseEntity.badRequest().body(new Erro("Email e/ou Senha Incorreto(s)"));
        }
    }
}
