package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.TipoPagamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TipoPagamento {
    @Autowired
    private TipoPagamentoDAO tipoPagamentoDAO;

    //atributos da tabela
    private int cod;
    private String descricao;

    // Construtores
    public TipoPagamento(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public TipoPagamento() {
        this(0, "");
    }

    // Gets e Sets
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // CRUD ------------------------------------------------------------------
    public List<TipoPagamento> consultar(String filtro, Conexao conexao) {
        return tipoPagamentoDAO.get(filtro, conexao);
    }

    public TipoPagamento consultarID(int id, Conexao conexao) {
        return tipoPagamentoDAO.get(id, conexao);
    }

    public boolean incluir(Conexao conexao) {
        return tipoPagamentoDAO.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        TipoPagamentoDAO tipoPagDAO = new TipoPagamentoDAO();
        return tipoPagDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return tipoPagamentoDAO.alterar(this, conexao);
    }
}
