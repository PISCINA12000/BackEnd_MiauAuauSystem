package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.PlanoContasGerencialDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlanoContasGerencial {
    @Autowired
    private PlanoContasGerencialDAO planoContasGerencialDAO;

    //atributos da tabela
    private int cod;
    private String descricao;
    private int codPcr; //esse atributo irá referenciar a tabela PlanoContasReferencial

    // Construtores
    public PlanoContasGerencial(int cod, String descricao, int codPcr) {
        this.cod = cod;
        this.descricao = descricao;
        this.codPcr = codPcr;
        if(planoContasGerencialDAO == null)
            planoContasGerencialDAO = new PlanoContasGerencialDAO();
    }

    public PlanoContasGerencial() {
        this(0, "", 0);
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

    public int getCodPcr() {
        return codPcr;
    }

    public void setCodPcr(int codPcr) {
        this.codPcr = codPcr;
    }

    // CRUD ------------------------------------------------------------------
    public List<PlanoContasGerencial> consultar(String filtro, Conexao conexao) {
        return planoContasGerencialDAO.get(filtro, conexao);
    }

    public PlanoContasGerencial consultarID(int id, Conexao conexao) {
        return planoContasGerencialDAO.get(id, conexao);
    }

    public boolean incluir(Conexao conexao) {
        return planoContasGerencialDAO.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        return planoContasGerencialDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return planoContasGerencialDAO.alterar(this, conexao);
    }
}
