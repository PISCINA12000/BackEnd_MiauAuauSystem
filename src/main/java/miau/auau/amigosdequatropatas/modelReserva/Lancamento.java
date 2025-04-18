package miau.auau.amigosdequatropatas.modelReserva;

import java.io.RandomAccessFile;

public class Lancamento {
    private int cod;
    private int codUsuario;
    private int codTpLanc;
    private String data;
    private int debito;
    private int credito;
    private String descricao;
    private double valor;
    private RandomAccessFile PDF;

    // Construtores
    public Lancamento(int cod, int codUsuario, int codTpLanc, String data, int debito, int credito, String descricao, double valor, RandomAccessFile PDF) {
        this.cod = cod;
        this.codUsuario = codUsuario;
        this.codTpLanc = codTpLanc;
        this.data = data;
        this.debito = debito;
        this.credito = credito;
        this.descricao = descricao;
        this.valor = valor;
        this.PDF = PDF;
    }
    public Lancamento() {
        this(0,0,0,"",0,0,"",0,null);
    }

    // Gets e Sets
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getCodTpLanc() {
        return codTpLanc;
    }

    public void setCodTpLanc(int codTpLanc) {
        this.codTpLanc = codTpLanc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public RandomAccessFile getPDF() {
        return PDF;
    }

    public void setPDF(RandomAccessFile PDF) {
        this.PDF = PDF;
    }
}
