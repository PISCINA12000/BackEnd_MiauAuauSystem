package miau.auau.amigosdequatropatas.controller;

import miau.auau.amigosdequatropatas.entities.Lancamento;
import miau.auau.amigosdequatropatas.entities.PlanoContasReferencial;
import miau.auau.amigosdequatropatas.util.Conexao;
import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BalanceteController {
    public List<Map<String, Object>> onBalanceteAno(int ano){
        //criando a conexão
        SingletonDB singletonDB = SingletonDB.getInstance();
        Conexao conexao = singletonDB.getConexao();

        /*dentro desse controller irei precisar acessar a tabela
        *   lancamentos e a tabela tipoPagamento, com isso irei
        *   instanciar as suas modelos
        */
        PlanoContasReferencial planoContasReferencial = new PlanoContasReferencial();
        List<PlanoContasReferencial> planoContRef = planoContasReferencial.consultar("", conexao);
        /*
        * Agora com cada tipoPagamento em mãos, irei realizar o somatório na tabela de lancamentos
        *   sempre aonde tiver alguma linha com o código igual nas duas tabelas, porém será um
        *   somatório diferente para cada débito e crédito de cada tipoPagamento
        * */
        Lancamento lancamento  = new Lancamento();
        Map<String, Object> somaPcr;
        List<Map<String, Object>> balancete = new ArrayList<>();

        //cada iteração do for terá o somatório do débito e do crédito de determinado tpPag
        for (PlanoContasReferencial pcr : planoContRef) {
            Map<String, Object> linhaBalancete = new HashMap<>();

            linhaBalancete.put("classificacao", pcr.getClassificacao());
            linhaBalancete.put("referencial", pcr.getDescricao());

            //somar o debito desse tipoPagamento
            somaPcr = lancamento.somaTipoPag("debito",pcr.getCod(),ano,conexao);
            linhaBalancete.put("debito", somaPcr.get("soma"));

            //somar o credito desse tipoPagamento
            somaPcr = lancamento.somaTipoPag("credito",pcr.getCod(),ano,conexao);
            linhaBalancete.put("credito", somaPcr.get("soma"));

            //adicionar no balancete final
            balancete.add(linhaBalancete);
        }
        return balancete;
    }
}
