package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.DoacaoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;

import java.util.List;

public class Doacao {
    private int codDoacao;
    private Usuario usuario;
    private String status;
    private String data;
    private int valor;

    public Doacao(int codDoacao, Usuario usuario, String status, String data, int valor) {
        this.codDoacao = codDoacao;
        this.usuario = usuario;
        this.status = status;
        this.data = data;
        this.valor = valor;
    }

    public Doacao() {
        this(0, null, "", "", 0);
    }

    public int getCodDoacao() {
        return codDoacao;
    }

    public void setCodDoacao(int codDoacao) {
        this.codDoacao = codDoacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean incluir(Conexao conexao) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        return doacaoDAO.gravar(this, conexao); // grava no banco
    }

    public List<Doacao> consultar(String filtro, Conexao conexao) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        return doacaoDAO.get(filtro, conexao);
    }

    public Doacao consultarID(int id, Conexao conexao) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        return doacaoDAO.get(id, conexao);
    }

    public boolean excluir(Conexao conexao) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        return doacaoDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        DoacaoDAO doacaoDAO = new DoacaoDAO();
        return doacaoDAO.alterar(this, conexao);
    }

    public List<Doacao> consultarPorUsuario(int codUsuario, Conexao conexao) {
        return new DoacaoDAO().buscarPorUsuario(codUsuario, conexao);
    }

}