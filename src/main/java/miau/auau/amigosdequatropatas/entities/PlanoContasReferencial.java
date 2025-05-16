package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.PlanoContasReferencialDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlanoContasReferencial {
    @Autowired
    private PlanoContasReferencialDAO planoContasReferencialDAO;

    //atributos da tabela
    private int cod;
    private String descricao;
    private String natureza;
    private String classificacao;

    // Construtores
    public PlanoContasReferencial(int cod, String descricao, String natureza, String classificacao) {
        this.cod = cod;
        this.descricao = descricao;
        this.natureza = natureza;
        this.classificacao = classificacao;
        if(planoContasReferencialDAO == null)
            planoContasReferencialDAO = new PlanoContasReferencialDAO();
    }

    public PlanoContasReferencial() {
        this(0, "", "", "");
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

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    // CRUD ------------------------------------------------------------------
    public List<PlanoContasReferencial> consultar(String filtro, Conexao conexao) {
        return planoContasReferencialDAO.get(filtro, conexao);
    }

    public PlanoContasReferencial consultarID(int id, Conexao conexao) {
        return planoContasReferencialDAO.get(id, conexao);
    }

    public boolean incluir(Conexao conexao) {
        return planoContasReferencialDAO.gravar(this, conexao); // grava no banco
    }

    public boolean excluir(Conexao conexao) {
        return planoContasReferencialDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        return planoContasReferencialDAO.alterar(this, conexao);
    }
}
