package miau.auau.amigosdequatropatas.entities;
import miau.auau.amigosdequatropatas.dao.AdocaoDAO;
import miau.auau.amigosdequatropatas.util.Conexao;


public class Adocao {

    private Animal animal;
    private Usuario usuario;
    private String data;

    // Construtores

    public Adocao(Animal animal, Usuario usuario, String data) {
        this.animal = animal;
        this.usuario = usuario;
        this.data = data;
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

    public boolean incluir(Conexao conexao) {

        AdocaoDAO adocaoDAO = new AdocaoDAO();
        return adocaoDAO.gravar(this, conexao); // grava no banco
    }
}
