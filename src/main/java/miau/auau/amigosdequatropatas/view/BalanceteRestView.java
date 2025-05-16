package miau.auau.amigosdequatropatas.view;

import miau.auau.amigosdequatropatas.controller.BalanceteController;
import miau.auau.amigosdequatropatas.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("apis/balancete")
public class BalanceteRestView {
    @Autowired
    private BalanceteController balanceteController;

    @GetMapping("ano/{ano}")
    public ResponseEntity<Object> getBalancete(@PathVariable(value = "ano") int ano){
        List<Map<String,Object>> balancete;
        balancete = balanceteController.onBalanceteAno(ano);
        if(balancete != null && !balancete.isEmpty()){
            return ResponseEntity.ok(balancete);
        }
        return ResponseEntity.badRequest().body(new Erro("Não foi possível recuperar o balencete desse ano!!"));
    }
}
