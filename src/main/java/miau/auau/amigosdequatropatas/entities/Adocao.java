package miau.auau.amigosdequatropatas.entities;

import miau.auau.amigosdequatropatas.dao.AdocaoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;
import java.util.List;

public class Adocao {

    private int codAdocao;
    private Animal animal;
    private Usuario usuario;
    private String data;
    private String status;

    // Construtores
    public Adocao(int codAdocao, Animal animal, Usuario usuario, String data, String status) {
        this.codAdocao = codAdocao;
        this.animal = animal;
        this.usuario = usuario;
        this.data = data;
        this.status = status;
    }

    public Adocao() {
        this(0,null,null,"","");
    }

    public int getCodAdocao() {
        return codAdocao;
    }

    public void setCodAdocao(int codAdocao) {
        this.codAdocao = codAdocao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //CRUD
    public boolean incluir(Conexao conexao) {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.gravar(this, conexao); // grava no banco
    }

    public List<Adocao> consultar(String filtro, Conexao conexao) {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.get(filtro, conexao);
    }
    public List<String> consultarAnos(Conexao conexao)
    {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.getAnos(conexao);
    }
    public Adocao consultarID(int id, Conexao conexao) {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.get(id, conexao);
    }
    public List<Adocao> consultarAdocaoPeloUsuId(int id, String filtro,Conexao conexao)
    {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.getAdocaoPeloUsuId(id, filtro,conexao);
    }
    public boolean excluir(Conexao conexao) {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.apagar(this, conexao);
    }

    public boolean alterar(Conexao conexao) {
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.alterar(this, conexao);
    }
}
