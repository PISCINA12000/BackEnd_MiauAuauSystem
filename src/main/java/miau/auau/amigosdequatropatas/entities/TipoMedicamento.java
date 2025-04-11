package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.TipoMedicamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TipoMedicamento {

    @Autowired
    private TipoMedicamentoDAO tipoMedicamentoDAL;

    private int cod;
    private String nome;

    // Construtores
    public TipoMedicamento(int cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public TipoMedicamento() {
        this(0, "");
    }

    // Gets e Sets
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean incluir(Conexao conexao) {
        return tipoMedicamentoDAL.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        return tipoMedicamentoDAL.apagar(this, conexao);
    }

    public TipoMedicamento consultarID(int id, Conexao conexao) {
        return tipoMedicamentoDAL.get(id, conexao);
    }

    public List<TipoMedicamento> consultar(String filtro, Conexao conexao) {
        return tipoMedicamentoDAL.get(filtro, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return tipoMedicamentoDAL.alterar(this,conexao);
    }
}
