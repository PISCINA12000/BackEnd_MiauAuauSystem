package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AnimalDAO;
import miau.auau.amigosdequatropatas.dao.LancamentoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Lancamento {
    @Autowired
    private LancamentoDAO lancamentoDAO;

    //campos da tabela no banco de dados
    private int cod;
    private String data;
    private int codTpLanc;
    private int codAnimal;
    private int debito;
    private int credito;
    private String descricao;
    private double valor;
    private byte[] PDF;

    //CONSTRUTORES


    public Lancamento(int cod, String data, int codTpLanc, int codAnimal, int debito, int credito, String descricao, double valor, byte[] PDF) {
        this.cod = cod;
        this.data = data;
        this.codTpLanc = codTpLanc;
        this.codAnimal = codAnimal;
        this.debito = debito;
        this.credito = credito;
        this.descricao = descricao;
        this.valor = valor;
        this.PDF = PDF;
    }

    public Lancamento() {
        this(0,"",0,0,0,0,"",0,null);
    }

    //GETS E SETS
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCodTpLanc() {
        return codTpLanc;
    }

    public void setCodTpLanc(int codTpLanc) {
        this.codTpLanc = codTpLanc;
    }

    public int getCodAnimal() {
        return codAnimal;
    }

    public void setCodAnimal(int codAnimal) {
        this.codAnimal = codAnimal;
    }

    public int getDebito() {
        return debito;
    }

    public void setDebito(int debito) {
        this.debito = debito;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public byte[] getPDF() {
        return PDF;
    }

    public void setPDF(byte[] PDF) {
        this.PDF = PDF;
    }

    //CRUD
    public List<Lancamento> consultar(String filtro, Conexao conexao) {
        //return lancamentoDAO.get(filtro, conexao);
        return null; //temp
    }

    public Lancamento consultarID(int id, Conexao conexao) {
        //return lancamentoDAO.get(id, conexao);
        return null; //temp
    }

    public boolean incluir(Conexao conexao) {
        //return lancamentoDAO.gravar(this, conexao); // grava no banco
        return true; //temp
    }

    public boolean excluir(Conexao conexao) {
        LancamentoDAO lancDAO = new LancamentoDAO();
        //return lancDAO.apagar(this, conexao);
        return true; //temp
    }

    public boolean alterar(Conexao conexao) {
        //return lancamentoDAO.alterar(this, conexao);
        return true; //temp
    }
}
