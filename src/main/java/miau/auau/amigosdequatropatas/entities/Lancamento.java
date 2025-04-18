package miau.auau.amigosdequatropatas.entities;

import org.springframework.stereotype.Component;

import java.io.RandomAccessFile;

@Component
public class Lancamento {
    private int cod;
    private int codTpLanc;
    private int codAnimal;
    private String data;
    private int debito;
    private int credito;
    private String descricao;
    private double valor;
    private byte[] PDF;

    //CONSTRUTORES
    public Lancamento(int codTpLanc, int codAnimal, String data, int debito, int credito, String descricao, double valor, byte[] PDF) {
        this.codTpLanc = codTpLanc;
        this.codAnimal = codAnimal;
        this.data = data;
        this.debito = debito;
        this.credito = credito;
        this.descricao = descricao;
        this.valor = valor;
        this.PDF = PDF;
    }

    public Lancamento() {
        this(0,0,"",0,0,"",0,null);
    }

    //GETS E SETS
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
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

    public byte[] getPDF() {
        return PDF;
    }

    public void setPDF(byte[] PDF) {
        this.PDF = PDF;
    }

}
